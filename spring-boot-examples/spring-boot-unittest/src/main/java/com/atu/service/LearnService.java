package com.atu.service;

import com.atu.domain.LearnResource;
import com.atu.model.LeanQueryLeanListReq;
import com.atu.util.Page;

import java.util.List;


public interface LearnService extends IService<LearnResource> {
    public List<LearnResource> queryLearnResouceList(Page<LeanQueryLeanListReq> page);

    public void deleteBatch(Long[] ids);

    public void addBatch();
}
