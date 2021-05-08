package com.atu.design.codeoptimize.service;

import com.atu.design.codeoptimize.enums.CompanyEnum;
import com.atu.design.codeoptimize.service.AbstractCompanyCommonService;
import com.atu.design.codeoptimize.vo.Request;
import com.atu.design.codeoptimize.vo.Response;

/**
 * @Author: Tom
 * @Date: 2021/5/7 5:47 下午
 * @Description: 策略模式：定义个方法/接口，让子类自己去实现
 */
public class CompanyCServiceImpl  extends AbstractCompanyCommonService {
    @Override
    boolean isRequestByProxy() {
        return false;
    }

    /**
     * 前面商户A和商户B还是不变，使用抽象类AbstractCompanyCommonService的模板，
     * 模板不满足商户C，商户C只能自己去实现咯，各个子类自己去实现的行为，就是策略模式的体现
     * @param request
     * @return
     */
    @Override
    public Response handler(Request request) {
        //查询商户信息
        queryMerchantInfo();
        requestByWebservice();
        return null;
    }

    @Override
    public CompanyEnum getCompanyEnum() {
        return CompanyEnum.C;
    }

    public void requestByWebservice() {
        //...
    }
}
