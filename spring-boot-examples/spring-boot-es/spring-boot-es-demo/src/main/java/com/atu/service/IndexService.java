package com.atu.service;

import java.io.IOException;

/**
 * @author: Tom
 * @date: 2022/6/15 15:00
 * @description: TODO
 **/
public interface IndexService {
    void createIndex() throws IOException;

    void bulkCreateIndex() throws IOException;

    void updateIndex() throws IOException;

    void insertOrUpdateOne();

    void deleteIndex() throws IOException;

    void deleteByQuery();

    void bulkDiff();

    void selectByUser();

    void selectByPage(Integer from, Integer pageSize);
}
