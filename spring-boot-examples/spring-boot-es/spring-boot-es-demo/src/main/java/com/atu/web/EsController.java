package com.atu.web;

import com.atu.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @author: Tom
 * @date: 2022/6/15 15:11
 * @description: TODO
 **/
@Controller
public class EsController {

    @Autowired
    private IndexService indexService;

    @ResponseBody
    @RequestMapping("/test/createIndex")
    public void createIndex() throws IOException {
        indexService.createIndex();
    }

    @ResponseBody
    @RequestMapping("/test/bulkCreateIndex")
    public void bulkCreateIndex() throws IOException {
        indexService.bulkCreateIndex();
    }

    @ResponseBody
    @RequestMapping("/test/updateIndex")
    public void updateIndex() throws IOException {
        indexService.updateIndex();
    }

    @ResponseBody
    @RequestMapping("/test/insertOrUpdateOne")
    public void insertOrUpdateOne() {
        indexService.insertOrUpdateOne();
    }

    @ResponseBody
    @RequestMapping("/test/deleteIndex")
    public void deleteIndex() throws IOException {
        indexService.deleteIndex();
    }

    @ResponseBody
    @RequestMapping("/test/deleteByQuery")
    public void deleteByQuery() {
        indexService.deleteByQuery();
    }

    @ResponseBody
    @RequestMapping("/test/bulkDiff")
    public void bulkDiff() {
        indexService.bulkDiff();
    }

    @ResponseBody
    @RequestMapping("/test/selectByUser")
    public void selectByUser() {
        indexService.selectByUser();
    }

    @ResponseBody
    @RequestMapping("/test/selectByPage")
    public void selectByPage(Integer from, Integer pageSize) {
        indexService.selectByPage(from, pageSize);
    }
}
