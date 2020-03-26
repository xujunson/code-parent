package com.atu.ad.advice;

import com.atu.ad.exception.AdException;
import com.atu.ad.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Tom
 * @date: 2020-03-26 18:01
 * @description: 统一异常处理
 */

/**
 * 统一异常处理的解释说明 @ExceptionHandler
 * 拦截 Spring 的异常处理需要使用到 ExceptionHandler 注解。
 * Spring 3.0 引入的这个注解可以让我们在一个 handler 方法或者类中统一处理 Controller 抛出的异常，使得写出的代码更加清晰。
 * 首先，来看看对这个注解的解释：
 *
 * @Target(ElementType.METHOD) // 这个注解标注在对象的方法上
 * @Retention(RetentionPolicy.RUNTIME) // 在运行时有效
 * @Documented
 * public @interface ExceptionHandler {
 * Exceptions handled by the annotated method. If empty, will default to any
 * exceptions listed in the method argument list.
 * value 是一个 Class 数组，用于指定需要拦截的异常类。
 *
 * Class<?extends Throwable>[]value()default {};
 * }
 * 说明：该注解注释的方法可以有灵活的输入参数，通常需要给出异常参数：
 * 包括一般的异常或特定的异常（即自定义异常），如果注解自身没有指定异常类，会默认进行映射。
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * 对 AdException 进行统一处理
     * ExceptionHandler: 可以对指定的异常进行拦截
     * 优化: 定义多种类异常, 并实现对应的异常处理. 例如: 推广单元的操作出现异常, 抛出 AdUnitException;
     * Binlog 解析异常, 抛出 BinlogException
     *
     * @param req
     * @param ex
     * @return
     */
    @ExceptionHandler(value = AdException.class)
    public CommonResponse<String> handlerAdException(HttpServletRequest req, AdException ex) {
        // 统一异常接口的响应
        // 优化: 定义不同类型的异常枚举(异常码和异常信息)
        CommonResponse<String> response = new CommonResponse<>(-1, "business error");
        response.setData(ex.getMessage());
        return response;
    }
}
