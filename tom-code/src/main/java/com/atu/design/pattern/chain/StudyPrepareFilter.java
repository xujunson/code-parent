package com.atu.design.pattern.chain;

/**
 * @Author: Tom
 * @Date: 2021/7/2 1:52 下午
 * @Description: 责任链模式
 */
public interface StudyPrepareFilter {
    public void doFilter(PreparationList preparationList, FilterChain filterChain);
}
