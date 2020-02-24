package com.atu.dao;

import com.atu.domain.LearnResouce;
import com.atu.tools.Page;
import java.util.Map;

public interface LearnDao {
    int add(LearnResouce learnResouce);

    int update(LearnResouce learnResouce);

    int deleteByIds(String ids);

    LearnResouce queryLearnResouceById(Long id);

    Page queryLearnResouceList(Map<String, Object> params);
}
