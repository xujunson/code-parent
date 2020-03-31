package com.atu.search.vo.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: Tom
 * @date: 2020-03-31 10:22
 * @description: 维度匹配信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistrictFeature {

    private List<ProvinceAndCity> districts;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProvinceAndCity {

        private String province;
        private String city;
    }
}
