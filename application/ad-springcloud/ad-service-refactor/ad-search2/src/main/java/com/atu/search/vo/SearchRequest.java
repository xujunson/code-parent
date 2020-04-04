package com.atu.search.vo;

import com.atu.search.vo.feature.DistrictFeature;
import com.atu.search.vo.feature.FeatureRelation;
import com.atu.search.vo.feature.ItFeature;
import com.atu.search.vo.feature.KeywordFeature;
import com.atu.search.vo.media.AdSlot;
import com.atu.search.vo.media.App;
import com.atu.search.vo.media.Device;
import com.atu.search.vo.media.Geo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: Tom
 * @date: 2020-03-31 9:59
 * @description: 媒体方的一次请求的对象定义
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    //媒体方的请求标识
    private String mediaId;

    //请求基本信息
    private RequestInfo requestInfo;

    //匹配信息
    private FeatureInfo featureInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestInfo {
        private String requestId;
        private List<AdSlot> adSlots; //广告位
        private App app;
        private Geo geo;
        private Device device;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeatureInfo {
        private KeywordFeature keywordFeature;
        private DistrictFeature districtFeature;
        private ItFeature itFeature;
        private FeatureRelation relation = FeatureRelation.AND; //feature之间关系
    }
}

