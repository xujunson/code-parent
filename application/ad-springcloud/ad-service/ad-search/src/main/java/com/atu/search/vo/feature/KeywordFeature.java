package com.atu.search.vo.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: Tom
 * @date: 2020-03-31 10:22
 * @description: 关键词匹配信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeywordFeature {
    private List<String> keywords;
}
