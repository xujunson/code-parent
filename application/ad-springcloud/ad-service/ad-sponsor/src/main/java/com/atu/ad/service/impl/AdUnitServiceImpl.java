package com.atu.ad.service.impl;

import com.atu.ad.constant.Constants;
import com.atu.ad.dao.AdPlanRepository;
import com.atu.ad.dao.AdUnitRepository;
import com.atu.ad.entity.AdPlan;
import com.atu.ad.entity.AdUnit;
import com.atu.ad.exception.AdException;
import com.atu.ad.service.IAdUnitService;
import com.atu.ad.vo.AdUnitRequest;
import com.atu.ad.vo.AdUnitResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author: Tom
 * @date: 2020-03-28 11:09
 * @description: 推广单元服务接口
 */
@Service
public class AdUnitServiceImpl implements IAdUnitService {

    private AdPlanRepository planRepository;

    private AdUnitRepository unitRepository;

    @Autowired
    public AdUnitServiceImpl(AdPlanRepository planRepository, AdUnitRepository unitRepository) {
        this.planRepository = planRepository;
        this.unitRepository = unitRepository;
    }

    /**
     * 创建推广单元
     *
     * @param request
     * @return
     * @throws AdException
     */
    @Override
    public AdUnitResponse createUnit(AdUnitRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        Optional<AdPlan> adPlan = planRepository.findById(request.getPlanId());
        if (!adPlan.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        AdUnit oldUnit = unitRepository.findByPlanIdAndUnitName(request.getPlanId(), request.getUnitName());
        if (oldUnit != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_ERROR);
        }
        AdUnit newAdUnit = unitRepository.save(new AdUnit(request.getPlanId(), request.getUnitName(), request.getPositionType(), request.getBudget()));

        return new AdUnitResponse(newAdUnit.getId(), newAdUnit.getUnitName());
    }
}
