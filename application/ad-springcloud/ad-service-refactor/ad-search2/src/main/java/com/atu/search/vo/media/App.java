package com.atu.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Tom
 * @date: 2020-03-31 10:06
 * @description: 应用基本信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class App {
    //应用编码
    private String appCode;

    //应用名称
    private String appName;

    //应用包名
    private String packageName;

    //activity名称
    private String activityName;
}
