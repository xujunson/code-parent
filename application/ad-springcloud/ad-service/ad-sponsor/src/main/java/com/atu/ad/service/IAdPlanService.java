package com.atu.ad.service;

import com.atu.ad.entity.AdPlan;
import com.atu.ad.exception.AdException;
import com.atu.ad.vo.AdPlanGetRequest;
import com.atu.ad.vo.AdPlanRequest;
import com.atu.ad.vo.AdPlanResponse;

import java.util.List;

/**
 * @author: Tom
 * @date: 2020-03-28 10:25
 * @description: 推广接口
 */
public interface IAdPlanService {
    /**
     * 创建推广计划
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 获取推广计划
     *
     * @param request
     * @return
     * @throws AdException
     */
    List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException;

    /**
     * 更新推广计划
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 删除推广计划
     *
     * @param request
     * @throws AdException
     */
    void deleteAdPlan(AdPlanRequest request) throws AdException;
}
