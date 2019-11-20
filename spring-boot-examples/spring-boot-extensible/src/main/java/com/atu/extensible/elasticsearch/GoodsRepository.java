package com.atu.extensible.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsRepository extends ElasticsearchRepository<Goods, Integer>{

    List<Goods> findByName(String name);
}
