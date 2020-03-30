package com.atu.mysql;

import com.alibaba.fastjson.JSON;
import com.atu.mysql.constant.OpType;
import com.atu.mysql.dto.ParseTemplate;
import com.atu.mysql.dto.TableTemplate;
import com.atu.mysql.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @author: Tom
 * @date: 2020-03-30 14:36
 * @description: 解析服务——解析模板文件实现映射关系
 */
@Slf4j
@Component
public class TemplateHolder {
    private ParseTemplate template;

    private String SQL_SCHEMA = "select table_schema, table_name, " +
            "column_name, ordinal_position from information_schema.columns " +
            "where table_schema = ? and table_name = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //loadJson方法的实现应该在容器启动时就应该会执行
    @PostConstruct
    private void init() {
        loadJson("template.json");
    }

    //对外服务，解析bin log 通过表名取到该表的信息
    public TableTemplate getTable(String tableName) {
        return template.getTableTemplateMap().get(tableName);
    }

    //加载template.json配置文件，以及索引到列名的映射
    private void loadJson(String path) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        //通过ClassLoader获取到Resource下面定义的文件，获取到输入流
        InputStream inputStream = cl.getResourceAsStream(path);

        try {
            Template template = JSON.parseObject(
                    inputStream,
                    Charset.defaultCharset(),
                    Template.class
            );
            this.template = ParseTemplate.parse(template);
            loadMeta();
        } catch (IOException ex) {
            log.error(ex.getMessage());
            throw new RuntimeException("fail to parse json file");
        }

    }


    //实现查询模板中每张表的schema信息 完成与列名的映射
    private void loadMeta() {
        for (Map.Entry<String, TableTemplate> entry : template.getTableTemplateMap().entrySet()) {
            TableTemplate table = entry.getValue();
            List<String> updateFields = table.getOpTypeFieldSetMap().get(
                    OpType.UPDATE
            );
            List<String> insertFields = table.getOpTypeFieldSetMap().get(
                    OpType.ADD
            );
            List<String> deleteFields = table.getOpTypeFieldSetMap().get(
                    OpType.DELETE
            );
            jdbcTemplate.query(SQL_SCHEMA, new Object[]{
                    template.getDatabases(), table.getTableName()}, (rs, i) -> {

                int pos = rs.getInt("ORDINAL_POSITION");
                String colName = rs.getString("COLUMN_NAME"); //列名映射

                //对各个操作类型以及包含的字段判断，只要有一个满足就要实现填充
                if ((null != updateFields && updateFields.contains(colName))
                        || (null != insertFields && insertFields.contains(colName))
                        || (null != deleteFields && deleteFields.contains(colName))) {
                    table.getPosMap().put(pos - 1, colName);
                }
                return null;
            });
        }
    }
}
