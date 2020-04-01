package com.atu.ad.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author: Tom
 * @date: 2020-03-26 20:31
 * @description: Spring 的 WebMvcConfigurer 接口提供了很多方法让我们来定制 Spring MVC 的配置。
 * 这个接口还是非常常用的，可以对 Spring 的很多配置和行为进行定制。
 * 下面对一些常用的方法进行解释：
 * public interface WebMvcConfigurer {
 * 匹配路由请求规则
 * default void configurePathMatch(PathMatchConfigurer configurer){}
 * <p>
 * 注册自定义的 Formatter 和 Convert
 * default void addFormatters(FormatterRegistry registry){}
 * <p>
 * 添加静态资源处理器
 * default void addResourceHandlers(ResourceHandlerRegistry registry){}
 * <p>
 * 配置消息转换器
 * default void configureMessageConverters(List<HttpMessageConverter<?>>converters){}
 * <p>
 * 添加自定义视图控制器
 * default void addViewControllers(ViewControllerRegistry registry){}
 * <p>
 * 添加自定义方法参数处理器
 * default void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers){}
 * <p>
 * }
 */
//消息转换器
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * 配置消息转换器
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.clear(); //清空

        //只添加一个转换器
        //MappingJackson2CborHttpMessageConverter可以实现将java对象转为json对象
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
