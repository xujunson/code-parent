package com.atu.design.pattern.chain.filter;

import com.atu.design.pattern.chain.PreparationList;

/**
 * @Author: Tom
 * @Date: 2021/7/2 2:01 下午
 * @Description:
 */
public class HaveBreakfastFilter extends AbstractPrepareFilter {

    public HaveBreakfastFilter(AbstractPrepareFilter nextPrepareFilter) {
        super(nextPrepareFilter);
    }

    @Override
    public void prepare(PreparationList preparationList) {
        if (preparationList.isHaveBreakfast()) {
            System.out.println("吃早餐");
        }

    }

}
