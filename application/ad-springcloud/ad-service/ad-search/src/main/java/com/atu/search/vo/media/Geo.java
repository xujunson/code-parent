package com.atu.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Tom
 * @date: 2020-03-31 10:07
 * @description: 地理位置信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Geo {
    //纬度
    private Float latitude;

    //纬度
    private Float longitude;

    //所在城市
    private String city;
    //省份
    private String province;
}
