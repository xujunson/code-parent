package com.atu.service;

import com.atu.domain.LearnResource;
import com.atu.model.LeanQueryLeanListReq;
import com.atu.util.Page;

import java.util.List;

/**
 * Created by tengj on 2017/4/7.
 */

public interface LearnService extends IService<LearnResource> {
    public List<LearnResource> queryLearnResouceList(Page<LeanQueryLeanListReq> page);

    public void deleteBatch(Long[] ids);
}
