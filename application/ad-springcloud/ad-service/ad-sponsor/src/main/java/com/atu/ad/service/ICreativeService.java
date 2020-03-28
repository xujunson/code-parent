package com.atu.ad.service;

import com.atu.ad.vo.CreativeRequest;
import com.atu.ad.vo.CreativeResponse;

/**
 * @author: Tom
 * @date: 2020-03-28 11:56
 * @description:
 */
public interface ICreativeService {
    CreativeResponse createCreative(CreativeRequest request);
}
