package com.atu.design.pattern.chain.filter;

import com.atu.design.pattern.chain.PreparationList;

/**
 * @Author: Tom
 * @Date: 2021/7/2 1:58 下午
 * @Description:
 */
public class WashFaceFilter extends AbstractPrepareFilter {

    public WashFaceFilter(AbstractPrepareFilter nextPrepareFilter) {
        super(nextPrepareFilter);
    }

    @Override
    public void prepare(PreparationList preparationList) {
        if (preparationList.isWashFace()) {
            System.out.println("洗脸");
        }

    }

}
