package com.atu.mysql.dto;

import com.atu.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author: Tom
 * @date: 2020-03-30 13:35
 * @description: 对模板文件的对象表示
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParseTemplate {
    //模板文件声明的数据库
    private String databases;

    //对模板文件中定义的所有表，以及表的属性 的表达
    private Map<String, TableTemplate> tableTemplateMap = new HashMap<>();

    //实现填充
    public static ParseTemplate parse(Template _template) {
        ParseTemplate template = new ParseTemplate();
        template.setDatabases(_template.getDatabase());
        for (JsonTable table : _template.getTableList()) {
            String name = table.getTableName();
            Integer level = table.getLevel(); //层级

            TableTemplate tableTemplate = new TableTemplate();
            tableTemplate.setTableName(name);
            tableTemplate.setLevel(level.toString());
            template.tableTemplateMap.put(name, tableTemplate);

            //遍历操作类型对应的列
            Map<OpType, List<String>> opTypeFieldSetMap = tableTemplate.getOpTypeFieldSetMap();

            for (JsonTable.Column column : table.getInsert()) {
                //添加各个列

                //对Insert里面的所有列进行遍历，如果不存在就 ArrayList::new，如果存在就返回原来的ArrayList
                //并对这个list添加列
                getAndCreateIfNeed(OpType.ADD,
                        opTypeFieldSetMap,
                        ArrayList::new).add(column.getColumn());
            }
            for (JsonTable.Column column : table.getUpdate()) {
                getAndCreateIfNeed(OpType.UPDATE,
                        opTypeFieldSetMap,
                        ArrayList::new).add(column.getColumn());
            }
            for (JsonTable.Column column : table.getDelete()) {
                getAndCreateIfNeed(OpType.DELETE,
                        opTypeFieldSetMap,
                        ArrayList::new).add(column.getColumn());

            }
        }
        return template;
    }

    /**
     * 从map中按key取值，如果不存在则会创建并加入到map中，并返回创建的对象
     * <p>
     * supplier是个接口，有一个get()方法 每次调用get()方法时都会调用构造方法创建一个新对象
     *
     * @param key
     * @param map
     * @param factory
     * @param <T>
     * @param <R>
     * @return
     */
    private static <T, R> R getAndCreateIfNeed(T key, Map<T, R> map, Supplier<R> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }

}
