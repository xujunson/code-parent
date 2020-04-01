package com.atu.search.impl;

import com.alibaba.fastjson.JSON;
import com.atu.index.CommonStatus;
import com.atu.index.DataTable;
import com.atu.index.adunit.AdUnitIndex;
import com.atu.index.adunit.AdUnitObject;
import com.atu.index.creative.CreativeIndex;
import com.atu.index.creative.CreativeObject;
import com.atu.index.creativeunit.CreativeUnitIndex;
import com.atu.index.district.UnitDistrictIndex;
import com.atu.index.interest.UnitItIndex;
import com.atu.index.keyword.UnitKeywordIndex;
import com.atu.search.ISearch;
import com.atu.search.vo.SearchRequest;
import com.atu.search.vo.SearchResponse;
import com.atu.search.vo.feature.DistrictFeature;
import com.atu.search.vo.feature.FeatureRelation;
import com.atu.search.vo.feature.ItFeature;
import com.atu.search.vo.feature.KeywordFeature;
import com.atu.search.vo.media.AdSlot;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: Tom
 * @date: 2020-03-31 11:05
 * @description:
 */
@Slf4j
@Service
public class SearchImpl implements ISearch {
    //异常回退方法
    //通过 EnableCircuitBreaker注解实现
    //这个注解会通过AOP拦截所有的HystrixCommand注解的方法，是的HystrixCommand能够集成到springboot里面
    //并且将注解的方法(fetchAds)扔到Hystrix的线程池中。发生失败的时候，通过反射去调用回退方法(fallback)，然后实现断路。
    public SearchResponse fallback(SearchRequest request, Throwable e) {
        return null;
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallback")
    public SearchResponse fetchAds(SearchRequest request) {
        //1、取出请求的广告位信息
        List<AdSlot> adSlots = request.getRequestInfo().getAdSlots();

        //2、获取传递的三个Feature
        KeywordFeature keywordFeature = request.getFeatureInfo().getKeywordFeature();
        DistrictFeature districtFeature = request.getFeatureInfo().getDistrictFeature();
        ItFeature itFeature =
                request.getFeatureInfo().getItFeature();
        FeatureRelation relation = request.getFeatureInfo().getRelation();

        //构造响应对象
        SearchResponse response = new SearchResponse();
        Map<String, List<SearchResponse.Creative>> adSlot2Ads = response.getAdSlot2Ads();

        for (AdSlot adSlot : adSlots) {
            Set<Long> targetUnitIdSet;
            //根据流量类型获取初始的AdUnit
            Set<Long> adUnitIdSet = DataTable.of(AdUnitIndex.class).match(adSlot.getPositionType());

            //根据Feature实现过滤
            if (relation == FeatureRelation.AND) {
                filterKeywordFeature(adUnitIdSet, keywordFeature);
                filterDistrictFeature(adUnitIdSet, districtFeature);
                filterItTagFeature(adUnitIdSet, itFeature);

                //获取adUnitIdSet
                targetUnitIdSet = adUnitIdSet;
            } else {
                targetUnitIdSet = getORRelationUnitIds(adUnitIdSet,
                        keywordFeature,
                        districtFeature,
                        itFeature);
            }
            List<AdUnitObject> unitObjects = DataTable.of(AdUnitIndex.class).fetch(targetUnitIdSet);

            filterAdUnitAndPlanStatus(unitObjects, CommonStatus.VALID);

            List<Long> adIds = DataTable.of(CreativeUnitIndex.class).selectAds(unitObjects);
            List<CreativeObject> creatives = DataTable.of(CreativeIndex.class).fetch(adIds);

            //过滤——通过 AdSlot实现对CreativeObject的过滤
            filterCreativeByAdSlot(creatives, adSlot.getWidth(), adSlot.getHeight(), adSlot.getType());

            //实现将当前的广告检索系统返回广告创意信息
            adSlot2Ads.put(adSlot.getAdSlotCode(), buildCreativeResponse(creatives));
        }
        log.info("fetchAds: {}-{}",
                JSON.toJSONString(request),
                JSON.toJSONString(response));
        return response;
    }

    /**
     * 实现or类型过滤
     *
     * @param adUnitIdSet
     * @param keywordFeature
     * @param districtFeature
     * @param itFeature
     * @return
     */
    private Set<Long> getORRelationUnitIds(Set<Long> adUnitIdSet,
                                           KeywordFeature keywordFeature,
                                           DistrictFeature districtFeature,
                                           ItFeature itFeature) {

        if (CollectionUtils.isEmpty(adUnitIdSet)) {
            return Collections.emptySet();
        }

        Set<Long> keywordUnitIdSet = new HashSet<>(adUnitIdSet);
        Set<Long> districtUnitIdSet = new HashSet<>(adUnitIdSet);
        Set<Long> itUnitIdSet = new HashSet<>(adUnitIdSet);

        filterKeywordFeature(keywordUnitIdSet, keywordFeature);
        filterDistrictFeature(districtUnitIdSet, districtFeature);
        filterItTagFeature(itUnitIdSet, itFeature);

        return new HashSet<>(
                CollectionUtils.union(
                        CollectionUtils.union(keywordUnitIdSet, districtUnitIdSet),
                        itUnitIdSet
                )
        );
    }

    private void filterKeywordFeature(Collection<Long> adUnitIds, KeywordFeature keywordFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }

        //keywords为空代表不需要feature对象过滤
        if (CollectionUtils.isNotEmpty(keywordFeature.getKeywords())) {
            //adUnitIds —— 需要过滤的集合
            //第二个参数是一个判断条件，对adUnitIds执行一个for循环，去判断match是否能够通过，
            // 如果通过的话则不进行过滤，否则将adUnitId从adUnitIds里面移除掉
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId -> DataTable.of(UnitKeywordIndex.class).match(adUnitId, keywordFeature.getKeywords())
            );
        }
    }

    /**
     * 地域feature的过滤方法
     *
     * @param adUnitIds
     * @param districtFeature
     */
    private void filterDistrictFeature(
            Collection<Long> adUnitIds, DistrictFeature districtFeature
    ) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }

        if (CollectionUtils.isNotEmpty(districtFeature.getDistricts())) {

            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId ->
                            DataTable.of(UnitDistrictIndex.class)
                                    .match(adUnitId,
                                            districtFeature.getDistricts())
            );
        }
    }

    /**
     * 根据兴趣的方法实现过滤
     *
     * @param adUnitIds
     * @param itFeature
     */
    private void filterItTagFeature(Collection<Long> adUnitIds,
                                    ItFeature itFeature) {

        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }

        if (CollectionUtils.isNotEmpty(itFeature.getIts())) {

            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId ->
                            DataTable.of(UnitItIndex.class)
                                    .match(adUnitId,
                                            itFeature.getIts())
            );
        }
    }

    /**
     * 实现对状态的过滤
     *
     * @param unitObjects
     * @param status
     */
    private void filterAdUnitAndPlanStatus(List<AdUnitObject> unitObjects,
                                           CommonStatus status) {

        if (CollectionUtils.isEmpty(unitObjects)) {
            return;
        }

        CollectionUtils.filter(
                unitObjects,
                object -> object.getUnitStatus().equals(status.getStatus())
                        && object.getAdPlanObject().getPlanStatus().equals(status.getStatus())
        );
    }

    /**
     * 过滤
     *
     * @param creatives
     * @param width
     * @param height
     * @param type
     */
    private void filterCreativeByAdSlot(List<CreativeObject> creatives,
                                        Integer width,
                                        Integer height,
                                        List<Integer> type) {

        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }

        CollectionUtils.filter(
                creatives,
                creative ->
                        creative.getAuditStatus().equals(CommonStatus.VALID.getStatus())
                                && creative.getWidth().equals(width)
                                && creative.getHeight().equals(height)
                                && type.contains(creative.getType())
        );
    }

    /**
     * 返回创意响应信息
     *
     * @param creatives
     * @return
     */
    private List<SearchResponse.Creative> buildCreativeResponse(
            List<CreativeObject> creatives
    ) {

        if (CollectionUtils.isEmpty(creatives)) {
            return Collections.emptyList();
        }

        //随机获取一个
        CreativeObject randomObject = creatives.get(
                Math.abs(new Random().nextInt()) % creatives.size()
        );

        return Collections.singletonList(
                //转换
                SearchResponse.convert(randomObject)
        );
    }
}
