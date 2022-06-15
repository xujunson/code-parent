package com.atu.service.impl;

import com.alibaba.fastjson.JSON;
import com.atu.document.Hero;
import com.atu.service.IndexService;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Tom
 * @date: 2022/6/15 15:00
 * @description: TODO
 **/
@Slf4j
@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private RestHighLevelClient client;

    @Override
    public void createIndex() throws IOException {
        IndexRequest request = new IndexRequest("hero");
        request.id("1");
        Map<String, String> map = new HashMap<>();
        map.put("id", "1");
        map.put("name", "曹操");
        map.put("country", "魏");
        map.put("birthday", "公元156年");
        map.put("longevity", "65");
        request.source(map);
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        log.info("createIndex:{}", indexResponse.getResult());
    }

    @Override
    public void bulkCreateIndex() throws IOException {
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest("hero").id("2")
                .source(XContentType.JSON, "id", "2", "name", "刘备", "country", "蜀", "birthday", "公元161年", "longevity", "61"));
        request.add(new IndexRequest("hero").id("3")
                .source(XContentType.JSON, "id", "3", "name", "孙权", "country", "吴", "birthday", "公元182年", "longevity", "61"));
        request.add(new IndexRequest("hero").id("4")
                .source(XContentType.JSON, "id", "4", "name", "诸葛亮", "country", "蜀", "birthday", "公元181年", "longevity", "53"));
        request.add(new IndexRequest("hero").id("5")
                .source(XContentType.JSON, "id", "5", "name", "司马懿", "country", "魏", "birthday", "公元179年", "longevity", "72"));
        request.add(new IndexRequest("hero").id("6")
                .source(XContentType.JSON, "id", "6", "name", "荀彧", "country", "魏", "birthday", "公元163年", "longevity", "49"));
        request.add(new IndexRequest("hero").id("7")
                .source(XContentType.JSON, "id", "7", "name", "关羽", "country", "蜀", "birthday", "公元160年", "longevity", "60"));
        request.add(new IndexRequest("hero").id("8")
                .source(XContentType.JSON, "id", "8", "name", "周瑜", "country", "吴", "birthday", "公元175年", "longevity", "35"));
        BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
        log.info("bulkCreateIndex:{}", bulkResponse.hasFailures());

    }

    @Override
    public void updateIndex() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("country", "魏");
        UpdateRequest request = new UpdateRequest("hero", "7").doc(jsonMap);
        UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
        log.info("updateIndex:{}", updateResponse.getResult());
    }

    @Override
    public void insertOrUpdateOne() {
        Hero hero = new Hero();
        hero.setId(5);
        hero.setName("曹丕");
        hero.setCountry("魏");
        hero.setBirthday("公元187年");
        hero.setLongevity(39);
        IndexRequest request = new IndexRequest("hero");
        request.id(hero.getId().toString());
        request.source(JSON.toJSONString(hero), XContentType.JSON);
        try {
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);   //  1
            log.info("insertOrUpdateOne:{}", indexResponse.getResult());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteIndex() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("hero");
        deleteRequest.id("1");
        DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
        log.info("deleteIndex:{}", deleteResponse.getResult());
    }

    @Override
    public void deleteByQuery() {
        DeleteByQueryRequest request = new DeleteByQueryRequest("hero");
        request.setConflicts("proceed");
        request.setQuery(new TermQueryBuilder("country", "魏"));
        try {
            BulkByScrollResponse bulkResponse =
                    client.deleteByQuery(request, RequestOptions.DEFAULT);
            log.info("deleteByQuery:{}", bulkResponse.getBulkFailures().size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bulkDiff() {
        BulkRequest request = new BulkRequest();
        request.add(new DeleteRequest("hero", "3"));
        request.add(new UpdateRequest("hero", "7")
                .doc(XContentType.JSON, "longevity", "70"));
        BulkResponse bulkResponse = null;
        try {
            bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BulkItemResponse[] bulkItemResponses = bulkResponse.getItems();
        for (BulkItemResponse item : bulkItemResponses) {
            DocWriteResponse itemResponse = item.getResponse();
            switch (item.getOpType()) {
                case UPDATE:
                    UpdateResponse updateResponse = (UpdateResponse) itemResponse;
                    break;
                case DELETE:
                    DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
            }
            log.info("bulkDiff:{}", item.status());
        }
    }

    @Override
    public void selectByUser() {
        SearchRequest request = new SearchRequest("hero");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(new TermQueryBuilder("country", "魏"));
        // 相当于mysql里边的limit 1；
        builder.size(1);
        request.source(builder);
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            for (SearchHit hit : hits) {
                log.info("selectByUser:{}", JSON.toJSONString(hit.getSourceAsMap()));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void selectByPage(Integer from, Integer pageSize) {
        SearchRequest request = new SearchRequest("hero");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.termQuery("country", "蜀"));
        //boolQueryBuilder.must(QueryBuilders.rangeQuery("longevity").gte(50));
        sourceBuilder.query(boolQueryBuilder);
        sourceBuilder.from(from).size(pageSize);
        sourceBuilder.query(boolQueryBuilder);
        sourceBuilder.sort("longevity.keyword", SortOrder.ASC);
        request.source(sourceBuilder);
        SearchResponse response = null;
        try {
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Query by Condition execution failed: {}", e.getMessage(), e);
        }
        assert response != null;
        SearchHit[] hits = response.getHits().getHits();
        List<Hero> herosList = new ArrayList<>(hits.length);
        for (SearchHit hit : hits) {
            herosList.add(JSON.parseObject(hit.getSourceAsString(), Hero.class));
        }
        log.info("print info: {}, size: {}", herosList.toString(), herosList.size());
    }
}
