package com.atu.extensible.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源的配置。
 * 必须要继承AbstractRoutingDataSource，且只需实现determineCurrentLookupKey()方法，指定当前使用的数据源就行。
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSourceType();
    }
}
