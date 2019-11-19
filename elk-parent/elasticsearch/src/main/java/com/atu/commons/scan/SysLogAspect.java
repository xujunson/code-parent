package com.atu.commons.scan;

import com.atu.commons.annotation.LogAnnotation;
import com.atu.entity.sys.SysLog;
import com.atu.service.sys.SysLogService;
import com.atu.util.IpUtil;
import com.atu.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
/**
 * spring Aop扫描日志
 */
@Aspect
@Component
@Order
public class SysLogAspect {
    private static final Logger LOGGER = LogManager.getLogger(SysLogAspect.class);
    @Autowired private SysLogService sysLogService;
    //@within() 是一种静态切入点  即在IoC容器初始化完毕后就能确定的
    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void cutController(){}

    @Around(value = "cutController() && @annotation(log)",argNames = "point,log")
    public Object recordSysLog(ProceedingJoinPoint point,LogAnnotation log)throws Throwable{
        System.out.println("LogAnnotation:"+log.value());
        String methodName=point.getSignature().getName();//方法名
        String className=point.getTarget().getClass().getName();//类名
        System.out.println("类名："+className);
        System.out.println("方法名："+methodName);
        Object[] params=point.getArgs();//参数
        StringBuffer bfParams = new StringBuffer();
        Enumeration<String> paraNames = null;
        HttpServletRequest request = null;
        if(params!=null&&params.length>0){
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            System.out.println("ip="+ IpUtil.getIpAddr(request));
            paraNames=request.getParameterNames();
            String key;
            String value;
            while (paraNames.hasMoreElements()){
                key=paraNames.nextElement();
                value=request.getParameter(key);
                bfParams.append(key).append("=").append(value).append("&");
            }
            if(StringUtils.isBlank(bfParams)){
                bfParams.append(request.getQueryString());
            }
        }
        String msg=String.format("[类名]:%s,[方法名]%s,[参数]%s",className,methodName,bfParams.toString());
        LOGGER.info(msg);
        if(isWriteLog(methodName)){
            try {
                Subject subject= SecurityUtils.getSubject();
                PrincipalCollection principalCollection=subject.getPrincipals();
                if(principalCollection!=null){
                    SysLog sysLog=new SysLog();
                    sysLog.setUsername(principalCollection.getPrimaryPrincipal().toString());
                    sysLog.setCreateTime(new Date());
                    sysLog.setOptContent(msg);
                    sysLog.setDesc(log.value());
                    sysLog.setOptType(getOpType(methodName));
                    if (request != null) {
                        sysLog.setClientIp(request.getRemoteAddr());
                    }
                    LOGGER.info(sysLog.toString());
                    sysLogService.save(sysLog);
                }
            }catch (Exception e){
                LOGGER.error(e.getMessage(), e);
            }
        }
        return point.proceed();
    }

    private boolean isWriteLog(String method) {
        String[] pattern = {"login", "logout", "add", "edit", "delete", "grant"};
//        String[] pattern = {"login", "logout"};
        for (String s : pattern) {
            System.out.println(">>>"+s);
            if (method.indexOf(s) > -1) {
                return true;
            }
        }
        return false;
    }

    private String getOpType(String method){
        String[] pattern = {"login", "logout", "add", "edit", "delete", "grant"};
        for (String s : pattern) {
            if (method.indexOf(s) > -1) {
                return s;
            }
        }
        return null;
    }
}
