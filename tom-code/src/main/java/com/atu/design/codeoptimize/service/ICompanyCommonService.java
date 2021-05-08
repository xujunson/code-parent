package com.atu.design.codeoptimize.service;

import com.atu.design.codeoptimize.enums.CompanyEnum;
import com.atu.design.codeoptimize.vo.Request;
import com.atu.design.codeoptimize.vo.Response;

/**
 * @Author: Tom
 * @Date: 2021/5/7 5:35 下午
 * @Description:
 */
public interface ICompanyCommonService {
    Response handler(Request request);

    /**
     * 商户A、B、C服务怎么被管理呢，之前分别给A，B，C服务实现handler的，
     * 现在好了，都不知道怎么管理了，怎么知道调用哪个呢？别慌，解决方案是工厂方法模式。
     * 工厂方法模式具体实现就是：接口定义一个枚举，每个服务实现都重新实现枚举，设置唯一标志枚举，再交给spring容器管理
     *
     * @return
     */
    CompanyEnum getCompanyEnum();
}
