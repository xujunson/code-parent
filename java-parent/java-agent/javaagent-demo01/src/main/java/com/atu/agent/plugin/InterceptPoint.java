package com.atu.agent.plugin;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

/**
 * @author: Tom
 * @date: 2023/4/15 13:53
 * @description: TODO
 **/
public interface InterceptPoint {

    //类匹配规则
    ElementMatcher<TypeDescription> buildTypesMatcher();

    //方法匹配规则
    ElementMatcher<MethodDescription> buildMethodsMatcher();

}
