[静态资源和拦截器处理](http://tengj.top/2017/03/30/springboot6/)
[过滤器+监听器+拦截器+AOP 比较](https://segmentfault.com/a/1190000021823564)
[过滤器（Filter）与拦截器（Interceptor )区别](https://www.cnblogs.com/junzi2099/p/8022058.html)
[@webFilter使用@Order无效问题](https://www.cnblogs.com/okong/p/correct-webfilter.html)
[浅谈Java Web 之过滤器Filter](https://www.imooc.com/article/11667)
https://www.pianshen.com/article/9941145825/
https://blog.csdn.net/weixin_45677827/article/details/104743768
https://juejin.cn/post/6844903624187854862

Filter是Servlet规范里的高级特性，其不用于处理客户端请求，只用于对request和response进行修改并管理web服务器的所有资源
（通过对Jsp文件、Servlet文件、Html文件以及一些静态的图片等资源进行拦截实现）,实现URL级别的权限访问控制、过滤敏感词汇、压缩响应信息，
但是在编写Filter类时首先要实现javax.servlet.Filter接口。

过滤器是处于客户端与服务器资源文件之间的一道过滤网，在访问资源文件之前，通过一系列的过滤器对请求进行修改、判断等，把不符合规则的请求在中途拦截或修改。
也可以对响应进行过滤，拦截或修改响应。

Filter过滤器是Servlet容器层面的，在实现上基于函数回调，可以对几乎所有请求进行过滤。过滤器是对数据进行过滤，预处理过程，当我们访问网站时，有时候会发布一些敏感信息，
发完以后有的会用*替代，还有就是登陆权限控制等，一个资源，没有经过授权，肯定是不能让用户随便访问的，这个时候，也可以用到过滤器。
过滤器的功能还有很多，例如实现URL级别的权限控制、压缩响应信息、编码格式等等

三个方法：
/**
*web程序启动时调用此方法用于初始化Filter
*@param config 可以从参数中获取初始化参数以及ServletContext信息
*@throws ServletException
*/

//在整个web服务中此方法只被执行一次，即web程序启动时
public void init(FilterConfig config) throws ServletException;
/**
*客户请求服务器时经过（相当于一个门卡）
*@param request 客户请求
*@param response 服务器响应
*@param chain 滤镜链，通过 chain。doFilter(request,response)方法将请求传给下一个Filter或者Servlet

*@throws ServletException
*@throws IOException
*/

/*
在整个web服务过程中此方法将被执行多次，在每一次请求响应中都会被执
行
*/
public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain) throws ServletException,IOException;

/**
*web程序关闭时调用将销毁一些资源
*/

//整个web服务过程中只被调用一次，即关闭服务时
public void destroy();

作用：登录权限验证、资源访问权限控制、敏感词汇过滤、字符编码转换等等操作

SpringBoot实现过滤器，常见有三种方式，越复杂功能越强大。
1、无路径无顺序@Component
这种方式最简单，直接实现Filter接口，并使用@Component注解标注为组件自动注入bean。但是缺点是没办法设置过滤的路径，默认是 /* 过滤所有。
Filter接口有 init、doFilter、destroy 三个方法，但 init、destroy 是有默认方法实现，可以不重写。
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class DemoFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        System.out.println("filter1----------" + httpServletResponse.getStatus());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}

2、有路径无顺序@WebFilter+@ServletComponentScan
这种方式要稍微复杂一点，但更全面。使用 @WebFilter替代 @Component，可以通过该注解设置过滤器的匹配路径。不过需要在启动类中使用@ServletComponentScan。
@ServletComponentScan扫描带@WebFilter、@WebServlet、@WebListener并将帮我们注入bean。
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "filter1", urlPatterns = {"/hello/*"})
public class DemoFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        System.out.println("filter1----------" + httpServletResponse.getStatus());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}

3、有路径有顺序@Configuration
这种方式完全通过配置类来实现，在只实现过滤器的接口，并不需要通过任何注解注入IOC容器，都通过配置类来注入。

