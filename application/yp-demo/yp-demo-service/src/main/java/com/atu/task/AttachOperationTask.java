package com.atu.task;

import brave.Tracer;
import com.yunphant.utils.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author lidinglin
 * @version 1.0.0
 * @name AttachOperationTask
 * @date 2020/4/1 2:48 上午
 * @describe
 */
@Component
@Slf4j
public class AttachOperationTask {

    @Autowired
    Tracer tracer;

    @Async
    public void attachOperation(Map<String, Object> params, Map<String, Object> result) {
        log.debug("全局日志跟踪号:{}", tracer.currentSpan().context().traceIdString());
        log.debug("请求对象:{}", JSONUtil.toJsonStr(params));
        log.debug("应答对象:{}", JSONUtil.toJsonStr(result));
    }
}
