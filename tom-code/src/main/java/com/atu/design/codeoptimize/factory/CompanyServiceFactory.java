package com.atu.design.codeoptimize.factory;

import com.atu.design.codeoptimize.enums.CompanyEnum;
import com.atu.design.codeoptimize.service.ICompanyCommonService;
import com.atu.design.codeoptimize.vo.Request;
import com.atu.design.codeoptimize.vo.Response;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Tom
 * @Date: 2021/5/7 5:52 下午
 * @Description: 工厂方法模式
 * 在工厂模式中，我们在创建对象时不会对客户端暴露创建逻辑，并且是通过使用一个共同的接口来指向新创建的对象。
 */
@Component
public class CompanyServiceFactory implements ApplicationContextAware {
    private static Map<CompanyEnum, ICompanyCommonService> map = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, ICompanyCommonService> tempMap = applicationContext.getBeansOfType(ICompanyCommonService.class);
        tempMap.values().forEach(iCompanyCommonService -> map.put(iCompanyCommonService.getCompanyEnum(), iCompanyCommonService));
    }

    public Response handler(Request request) {
        Response response = new Response();
        return map.get(CompanyEnum.getCompanyEnum(request.getCompanyFlag())).handler(request);
    }
}