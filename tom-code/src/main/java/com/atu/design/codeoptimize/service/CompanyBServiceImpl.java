package com.atu.design.codeoptimize.service;

import com.atu.design.codeoptimize.enums.CompanyEnum;
import com.atu.design.codeoptimize.vo.Request;
import com.atu.design.codeoptimize.vo.Response;

/**
 * @Author: Tom
 * @Date: 2021/5/7 5:46 下午
 * @Description:
 */
public class CompanyBServiceImpl extends AbstractCompanyCommonService {
    /**
     * 公司B是不走代理的
     *
     * @return
     */
    @Override
    boolean isRequestByProxy() {
        return false;
    }

    @Override
    public Response handler(Request request) {
        Response response = new Response();
        return handlerTemplate(response, request);
    }

    @Override
    public CompanyEnum getCompanyEnum() {
        return CompanyEnum.B;
    }
}
