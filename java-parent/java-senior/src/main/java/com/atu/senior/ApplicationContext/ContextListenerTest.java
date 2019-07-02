package com.atu.senior.ApplicationContext;

/**
 * @Author: xsy
 * @Date: 2019/1/10 18:58
 * @Description: 应用上下文
 */

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListenerTest implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("*************destroy ContextListener*************");
    }

    @SuppressWarnings("unused")
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("*************init ContextListener*************");
        ServletContext servletContext = event.getServletContext();
        System.out.println("key:" + servletContext.getInitParameter("key"));
    }

}
