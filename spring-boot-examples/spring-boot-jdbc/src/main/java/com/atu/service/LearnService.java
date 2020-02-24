package com.atu.service;

import com.atu.domain.LearnResouce;
import com.atu.tools.Page;

import java.util.Map;

public interface LearnService {
    int add(LearnResouce learnResouce);

    int update(LearnResouce learnResouce);

    int deleteByIds(String ids);

    LearnResouce queryLearnResouceById(Long learnResouce);

    Page queryLearnResouceList(Map<String, Object> params);
}
