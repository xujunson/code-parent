package com.atu.design.pattern.chain.filter;

import com.atu.design.pattern.chain.PreparationList;

/**
 * @Author: Tom
 * @Date: 2021/7/2 1:59 下午
 * @Description:
 */
public class WashHairFilter extends AbstractPrepareFilter {

    public WashHairFilter(AbstractPrepareFilter nextPrepareFilter) {
        super(nextPrepareFilter);
    }

    @Override
    public void prepare(PreparationList preparationList) {
        if (preparationList.isWashHair()) {
            System.out.println("洗头");
        }

    }

}
