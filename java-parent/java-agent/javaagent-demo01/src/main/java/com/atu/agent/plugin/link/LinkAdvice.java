package com.atu.agent.plugin.link;

import com.atu.agent.trace.Span;
import com.atu.agent.trace.TrackContext;
import com.atu.agent.trace.TrackManager;
import net.bytebuddy.asm.Advice;

import java.util.UUID;

/**
 * @author: Tom
 * @date: 2023/4/15 13:54
 * @description: TODO
 **/
public class LinkAdvice {

    @Advice.OnMethodEnter()
    public static void enter(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName) {
        Span currentSpan = TrackManager.getCurrentSpan();
        if (null == currentSpan) {
            String linkId = UUID.randomUUID().toString();
            TrackContext.setLinkId(linkId);
        }
        TrackManager.createEntrySpan();
    }

    @Advice.OnMethodExit()
    public static void exit(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName) {
        Span exitSpan = TrackManager.getExitSpan();
        if (null == exitSpan) return;
        System.out.println("链路追踪(MQ)：" + exitSpan.getLinkId() + " " + className + "." + methodName + " 耗时：" + (System.currentTimeMillis() - exitSpan.getEnterTime().getTime()) + "ms");
    }

}
