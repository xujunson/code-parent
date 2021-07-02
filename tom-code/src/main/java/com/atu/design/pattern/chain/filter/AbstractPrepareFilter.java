package com.atu.design.pattern.chain.filter;

import com.atu.design.pattern.chain.PreparationList;
import com.atu.design.pattern.chain.Study;

/**
 * @Author: Tom
 * @Date: 2021/7/2 1:58 下午
 * @Description:
 */
public abstract class AbstractPrepareFilter {

    private AbstractPrepareFilter nextPrepareFilter;

    public AbstractPrepareFilter(AbstractPrepareFilter nextPrepareFilter) {
        this.nextPrepareFilter = nextPrepareFilter;
    }

    public void doFilter(PreparationList preparationList, Study study) {
        prepare(preparationList);

        if (nextPrepareFilter == null) {
            study.study();
        } else {
            nextPrepareFilter.doFilter(preparationList, study);
        }
    }

    public abstract void prepare(PreparationList preparationList);

}
