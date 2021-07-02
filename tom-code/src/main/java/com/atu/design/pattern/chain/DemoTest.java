package com.atu.design.pattern.chain;

import com.atu.design.pattern.chain.filter.AbstractPrepareFilter;
import com.atu.design.pattern.chain.filter.HaveBreakfastFilter;
import com.atu.design.pattern.chain.filter.WashFaceFilter;
import com.atu.design.pattern.chain.filter.WashHairFilter;
import org.junit.Test;

/**
 * @Author: Tom
 * @Date: 2021/7/2 2:01 下午
 * @Description: 责任链模式 https://www.cnblogs.com/xrq730/p/10633761.html
 */

public class DemoTest {
    @Test
    public void testResponsibility() {
        PreparationList preparationList = new PreparationList();
        preparationList.setWashFace(true);
        preparationList.setWashHair(false);
        preparationList.setHaveBreakfast(true);

        Study study = new Study();

        AbstractPrepareFilter haveBreakfastFilter = new HaveBreakfastFilter(null);
        AbstractPrepareFilter washFaceFilter = new WashFaceFilter(haveBreakfastFilter);
        AbstractPrepareFilter washHairFilter = new WashHairFilter(washFaceFilter);

        washHairFilter.doFilter(preparationList, study);
    }
}
