package com.atu.ad.advice;

import com.atu.ad.annotation.IgnoreResponseAdvice;
import com.atu.ad.vo.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author: Tom
 * @date: 2020-03-26 18:01
 * @description: 对响应的统一拦截
 */

/**
 * @RestControllerAdvice 注解 Advice 在 Spring 中的含义就是对 XX 功能增强，RestControllerAdvice（ControllerAdvice）就是对控制器增强。
 * 之所以使用 RestControllerAdvice，是因为我们对外提供的都是 Rest 接口（json）。
 * 我们需要对 Controller 返回的内容做一些额外的工作，即功能增强，就需要利用到这个注解。
 * 另外，这个注解提供了 basePackages 属性可以指定对特定 package 中的 Controller 生效。
 */
@RestControllerAdvice
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {

    /**
     * ResponseBodyAdvice 的作用是在响应体返回之前做一些自定义的处理工作。
     * 通常，我们会实现 ResponseBodyAdvice 接口，并包装统一的响应返回。
     * 接口的详细定义可以阅读 Spring 官网的解释:
     * Spring ResponseBodyAdvice  supports 方法 supports 的返回值是 boolean 类型，用于指定哪些 Controller 方法需要处理。
     * 我们当前的代码实现是标注了 IgnoreResponseAdvice 注解的类或者方法，统一响应不会生效。
     */

    /**
     * 判断是否需要对响应进行处理
     *
     * @param methodParameter
     * @param aClass
     * @return false: 不需要处理; true: 需要处理
     */
    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        //如果当前方法所在的类标识了 IgnoreResponseAdvice 注解, 则不需要处理
        if (methodParameter.getDeclaringClass().isAnnotationPresent(
                IgnoreResponseAdvice.class
        )) {
            return false;
        }

        //如果当前方法标识了 IgnoreResponseAdvice 注解, 则不需要处理
        if (methodParameter.getMethod().isAnnotationPresent(
                IgnoreResponseAdvice.class
        )) {
            return false;
        }
        return true;
    }


    /**
     * beforeBodyWrite 方法 根据方法的名字可以知道，这个方法实现了在结果输出前的操作。
     * 这个方法的参数很多，我们只需要关注第一个：Object，这个就是原始的 Controller 返回的内容。我们也就是需要对它进行包装。
     *
     * @param o
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(@Nullable Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        //定义最终的返回对象
        CommonResponse<Object> response = new CommonResponse<>(0, "");

        // 如果 o 是 null, response 不需要设置 data
        if (null == o) {
            return response;
        } else if (o instanceof CommonResponse) { // 如果 o 已经是 CommonResponse 类型, 强转即可
            response = (CommonResponse<Object>) o;
        } else { // 否则, 把响应对象作为 CommonResponse 的 data 部分
            response.setData(o);
        }
        return response;
    }
}
