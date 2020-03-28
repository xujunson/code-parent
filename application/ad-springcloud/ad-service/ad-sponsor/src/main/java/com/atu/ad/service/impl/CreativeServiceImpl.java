package com.atu.ad.service.impl;

import com.atu.ad.dao.CreativeRepository;
import com.atu.ad.entity.Creative;
import com.atu.ad.service.ICreativeService;
import com.atu.ad.vo.CreativeRequest;
import com.atu.ad.vo.CreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Tom
 * @date: 2020-03-28 12:01
 * @description: 创建 创意服务功能
 */
@Service
public class CreativeServiceImpl implements ICreativeService {
    private CreativeRepository creativeRepository;

    @Autowired
    public CreativeServiceImpl(CreativeRepository creativeRepository) {
        this.creativeRepository = creativeRepository;
    }

    @Override
    public CreativeResponse createCreative(CreativeRequest request) {
        Creative creative = creativeRepository.save(request.convertToEntity());

        return new CreativeResponse(creative.getId(), creative.getName());
    }
}
