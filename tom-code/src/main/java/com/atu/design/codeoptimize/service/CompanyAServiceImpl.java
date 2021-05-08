package com.atu.design.codeoptimize.service;

import com.atu.design.codeoptimize.enums.CompanyEnum;
import com.atu.design.codeoptimize.vo.Request;
import com.atu.design.codeoptimize.vo.Response;

/**
 * @Author: Tom
 * @Date: 2021/5/7 5:44 下午
 * @Description:
 */
public class CompanyAServiceImpl extends AbstractCompanyCommonService {

    /**
     * 公司A走代理
     *
     * @return
     */
    @Override
    boolean isRequestByProxy() {
        return true;
    }

    @Override
    public Response handler(Request request) {
        Response response = new Response();
        return handlerTemplate(response, request);
    }

    @Override
    public CompanyEnum getCompanyEnum() {
        return CompanyEnum.A;
    }
}
