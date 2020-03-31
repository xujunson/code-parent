package com.atu.search.vo;

import com.atu.index.creative.CreativeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Tom
 * @date: 2020-03-31 10:00
 * @description: 响应对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    //key——位编码，value——一个广告位可以返回多个信息
    private Map<String, List<Creative>> adSlot2Ads = new HashMap<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Creative {
        private Long adId;
        private String adUrl; //广告url
        private Integer width;
        private Integer height;
        private Integer type; //类型
        private Integer materialType;

        // 展示监测 url
        private List<String> showMonitorUrl =
                Arrays.asList("www.imooc.com", "www.imooc.com");
        // 点击监测 url
        private List<String> clickMonitorUrl =
                Arrays.asList("www.imooc.com", "www.imooc.com");
    }

    public static Creative convert(CreativeObject object) {

        Creative creative = new Creative();
        creative.setAdId(object.getAdId());
        creative.setAdUrl(object.getAdUrl());
        creative.setWidth(object.getWidth());
        creative.setHeight(object.getHeight());
        creative.setType(object.getType());
        creative.setMaterialType(object.getMaterialType());

        return creative;
    }

}
