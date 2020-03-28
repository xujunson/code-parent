package com.atu.ad.service;


import com.atu.ad.exception.AdException;
import com.atu.ad.vo.*;

public interface IAdUnitService {

    AdUnitResponse createUnit(AdUnitRequest request) throws AdException;

    /**
     * 推广单元三个维度的创建
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request)
            throws AdException;

    AdUnitItResponse createUnitIt(AdUnitItRequest request)
            throws AdException;

    AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request)
            throws AdException;

    /*CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request)
            throws AdException;*/
}
