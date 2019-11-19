package com.atu.commons.scan;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 获取spring上下文
 */
@SuppressWarnings("unchecked")
@Component
public class SpringContextUtil implements ApplicationContextAware,DisposableBean,ServletContextListener {
    private static ApplicationContext applicationContext;

    private static Logger logger = LoggerFactory.getLogger(SpringContextUtil.class);

    /**
     * 一启动监听就绑定值，属于静态类型的，刷新不会变化，多用于后端
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext application=sce.getServletContext();

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    /**
     * 取得存储在静态变量中的ApplicationContext.
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     * @param name
     * @return
     */
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     * @param requiredType
     * @return
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
    public static void clearHolder() {
        logger.debug("clean SpringContextHolder ApplicationContext:" + applicationContext);
        applicationContext = null;
    }

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        logger.debug("inject ApplicationContext to SpringContextHolder:{}", applicationContext);
        if (SpringContextUtil.applicationContext != null) {
            logger.warn("SpringContextHolder ApplicationContext covered, original ApplicationContext :" + SpringContextUtil.applicationContext);
        }
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 实现DisposableBean接口, 在Context关闭时清理静态变量.
     */
    @Override
    public void destroy() throws Exception {
        SpringContextUtil.clearHolder();
    }

    /**
     * 检查ApplicationContext不为空.
     */
    private static void assertContextInjected() {
        Validate.notNull(applicationContext != null, "applicaitonContext attribute not injected ,  applicationContext.xml assign SpringContextHolder.");
    }

    public static void publishEvent(ApplicationEvent event) {
		if (applicationContext == null) {
			return;
		}
		applicationContext.publishEvent(event);
	}
}
