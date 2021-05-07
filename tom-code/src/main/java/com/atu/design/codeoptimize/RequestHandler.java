package com.atu.design.codeoptimize;

import com.atu.design.codeoptimize.vo.Request;
import com.atu.design.codeoptimize.vo.Response;

/**
 * @Author: Tom
 * @Date: 2021/5/7 6:06 下午
 * @Description:
 */
public interface RequestHandler {
    Response handler(Request request);
}
