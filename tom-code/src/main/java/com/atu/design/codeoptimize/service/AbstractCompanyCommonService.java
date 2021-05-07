package com.atu.design.codeoptimize.service;

import com.atu.design.codeoptimize.vo.Request;
import com.atu.design.codeoptimize.vo.Response;

/**
 * @Author: Tom
 * @Date: 2021/5/7 5:36 下午
 * @Description:
 */
public abstract class AbstractCompanyCommonService implements ICompanyCommonService {
    /**
     * 模板方法
     *
     * @param response
     * @param request
     * @return
     */
    Response handlerTemplate(Response response, Request request) {
        //查询商户信息
        queryMerchantInfo();

        // 加签
        signature();

        // http请求
        if (isRequestByProxy()) {
            httpProxy();
        } else {
            httpDirect();
        }

        //验签
        verifySignature();
        return response;
    }

    void queryMerchantInfo() {

    }

    void signature() {

    }

    void httpProxy() {

    }

    void httpDirect() {

    }

    void verifySignature() {

    }

    /**
     * http是否走代理
     *
     * @return
     */
    abstract boolean isRequestByProxy();
}
