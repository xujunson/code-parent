package com.atu.service.impl;

import com.yunphant.aop.AttachOperationDelegate;
import com.atu.task.AttachOperationTask;
import com.yunphant.utils.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author lidinglin
 * @version 1.0.0
 * @name AttachOperationDelegateImpl
 * @date 2020/4/1 1:28 上午
 * @describe
 */
@Component
@Primary
@Slf4j
public class AttachOperationDelegateImpl implements AttachOperationDelegate {

    @Autowired
    AttachOperationTask attachOperationTask;

    /**
     * 接口方法附加操作
     *
     * @param invocation
     * @param params     接口调用请求参数,只认定继承YunphantRequest或Map的请求对象
     * @return
     * @throws Throwable
     */
    @Override
    public Object invokeAttachOperation(MethodInvocation invocation, Map<String, Object> params) throws Throwable {
        //todo:before
        Object result = invocation.proceed();
        //todo:after
        attachOperationTask.attachOperation(params, BeanUtil.beanToMap(result));
        return result;
    }
}
