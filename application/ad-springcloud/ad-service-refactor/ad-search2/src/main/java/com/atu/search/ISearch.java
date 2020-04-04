package com.atu.search;

import com.atu.search.vo.SearchRequest;
import com.atu.search.vo.SearchResponse;

/**
 * @author: Tom
 * @date: 2020-03-31 9:59
 * @description: 根据请求获取广告响应
 */
public interface ISearch {
    SearchResponse fetchAds(SearchRequest request);
}
