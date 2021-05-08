package com.atu.design.codeoptimize;

import com.atu.design.codeoptimize.factory.CompanyServiceFactory;
import com.atu.design.codeoptimize.vo.Request;
import com.atu.design.codeoptimize.vo.Response;

import javax.annotation.Resource;

/**
 * @Author: Tom
 * @Date: 2021/5/7 6:13 下午
 * @Description:
 */
public class CompanyHandler implements RequestHandler {
    @Resource
    CompanyServiceFactory companyServiceFactory;

    @Override
    public Response handler(Request request) {
        return companyServiceFactory.handler(request);
    }
}
