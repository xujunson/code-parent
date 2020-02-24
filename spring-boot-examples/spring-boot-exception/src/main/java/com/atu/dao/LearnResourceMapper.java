package com.atu.dao;

import com.atu.domain.LearnResource;
import com.atu.util.MyMapper;

import java.util.List;
import java.util.Map;

public interface LearnResourceMapper extends MyMapper<LearnResource> {
    List<LearnResource> queryLearnResouceList(Map<String, Object> map);
}