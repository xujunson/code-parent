package com.atu.mysql.listener;

import com.atu.mysql.constant.Constant;
import com.atu.mysql.constant.OpType;
import com.atu.mysql.dto.BinlogRowData;
import com.atu.mysql.dto.MySqlRowData;
import com.atu.mysql.dto.TableTemplate;
import com.atu.sender.ISender;
import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Tom
 * @date: 2020-03-30 16:34
 * @description: 增量数据处理器
 */
@Slf4j
@Component
public class IncrementListener implements Ilistener {

    @Resource(name = "indexSender")
    private ISender sender;

    @Autowired
    private AggregationListener aggregationListener;

    //将表进行注册
    @Override
    @PostConstruct //需要发生在IncrementListener实例化时
    public void register() {
        log.info("IncrementListener register db and table info");

        //k-表名， v-数据库名
        Constant.table2Db.forEach((k, v) ->
                aggregationListener.register(v, k, this));
    }

    /**
     * 将 BinlogRowData转换成MySqlRowData
     *
     * @param eventData
     */
    @Override
    public void onEvent(BinlogRowData eventData) {
        TableTemplate table = eventData.getTable();
        EventType eventType = eventData.getEventType();

        //包装成最后需要投递的数据
        MySqlRowData rowData = new MySqlRowData();
        rowData.setTableName(table.getTableName());
        rowData.setLevel(eventData.getTable().getLevel());
        OpType opType = OpType.to(eventType);
        rowData.setOpType(opType);

        //取出模板中该操作对应的字段列表
        List<String> fieldList = table.getOpTypeFieldSetMap().get(opType);
        if (null == fieldList) {
            log.warn("{} not support for {}", opType, table.getTableName());
            return;
        }

        //实现rowData转换赋值
        for (Map<String, String> afterMap : eventData.getAfter()) {
            Map<String, String> _afterMap = new HashMap();
            for (Map.Entry<String, String> entry : afterMap.entrySet()) {
                String colName = entry.getKey();
                String colValue = entry.getValue();
                _afterMap.put(colName, colValue);
            }
            rowData.getFieldValueMap().add(_afterMap);
        }
        sender.sender(rowData); //数据投递
    }
}
