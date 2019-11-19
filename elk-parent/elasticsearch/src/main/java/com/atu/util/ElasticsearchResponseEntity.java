package com.atu.util;

import java.util.List;

public class ElasticsearchResponseEntity<T> {
    private int from=0;
    private int size=10;
    private Long total;
    private List<T> records;

    public ElasticsearchResponseEntity(int from, int size) {
        this.from = from;
        this.size = size;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
