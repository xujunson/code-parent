package com.atu.index.interest;

import com.atu.index.IndexAware;
import com.atu.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author: Tom
 * @date: 2020-03-29 11:07
 * @description: 兴趣索引服务
 */
@Slf4j
@Component
public class UnitItIndex implements IndexAware<String, Set<Long>> {

    //通过itTag去查询它所关联的推广单元——反向索引
    private static Map<String, Set<Long>> itUnitMap;

    //通过unitId(推广单元id)查询到 itTag set，因为一个推广单元可以包含很多兴趣，同时一个兴趣也可以映射到多个推广单元
    private static Map<Long, Set<String>> unitItMap;

    static {
        itUnitMap = new ConcurrentHashMap<>();
        unitItMap = new ConcurrentHashMap<>();
    }


    @Override
    public Set<Long> get(String key) {
        //通过itTag获取它所关联的 unitIds
        return itUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitItIndex, before add: {}", unitItMap);
        Set<Long> unitIds = CommonUtils.getorCreate(
                key, itUnitMap, ConcurrentSkipListSet::new);
        unitIds.addAll(value);
        for (Long unitId : value) {
            Set<String> its = CommonUtils.getorCreate(
                    unitId, unitItMap, ConcurrentSkipListSet::new
            );
            its.add(key);
        }
        log.info("UnitItIndex, after add: {}", unitItMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("it index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("UnitItIndex, before delete: {}", unitItMap);

        //1、先获取key对应的uintIds
        Set<Long> unitIds = CommonUtils.getorCreate(
                key, itUnitMap,
                ConcurrentSkipListSet::new
        );
        //2、remove对应的uintIds
        unitIds.removeAll(value);

        for (Long unitId : value) {
            Set<String> itTagSet = CommonUtils.getorCreate(
                    unitId, unitItMap,
                    ConcurrentSkipListSet::new
            );
            itTagSet.remove(key);
        }
        log.info("UnitItIndex, after delete: {}", unitItMap);
    }

    public boolean match(Long unitId, List<String> itTags) {
        if(unitItMap.containsKey(unitId) && CollectionUtils.isNotEmpty(unitItMap.get(unitId))) {
            Set<String> unitKeywords = unitItMap.get(unitId);
            //itTags 是 unitKeywords的子集
            return CollectionUtils.isSubCollection(itTags, unitKeywords);
        }
        return false;
    }
}
