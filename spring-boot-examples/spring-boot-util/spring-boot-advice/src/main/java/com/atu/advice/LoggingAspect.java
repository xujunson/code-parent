package com.atu.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Author: Tom
 * @Date: 2021/7/9 10:11 上午
 * @Description:
 */
@Component
@Aspect
public class LoggingAspect {
    /**
     * 在每一个接口的实现类的每一个方法开始之前执行一段代码
     */

    @Before("execution(public int com.atu.service.ArithmeticCalculator.* (..))")
    public void beforeMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        System.out.println("The method "+methodName+" begins with "+ Arrays.asList(args));
    }

    @After("execution(public int com.atu.service.ArithmeticCalculator.* (..))")
    public void afterMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();

        System.out.println("The method "+methodName +" end......");
    }


    /**
     * 返回通知
     * 在方法正常结束后执行的代码
     * 返回通知是可以访问方法的返回值的！
     * @param joinPoint*/

    @AfterReturning(value = "execution(public int com.atu.service.ArithmeticCalculator.* (..))",
            returning = "result")
    public void afterReturning(JoinPoint joinPoint,Object result){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("The method "+methodName +" end......result:"+result);
    }


    /**
     * 在目标方法出现异常时会执行的代码
     * 可以访问到异常对象，且可以指定在出现特定异常时在执行通知代码
     * @param joinPoint
     * @param ex*/

    @AfterThrowing(value = "execution(public int com.atu.service.ArithmeticCalculator.* (..))", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("The method "+methodName +"occurs exception :" +ex);
    }

    /**
     * 环绕通知需要携带 ProceedingJoinPoint 类型的参数
     * 环绕通知类似于动态代理的全过程：ProceedingJoinPoint 类型的参数可以决定是否执行目标方法。
     * 且环绕通知必须有返回值，返回值即为目标方法的返回值
     * @param proceedingJoinPoint
     */
  /*@Around("execution(public int com.atguigu.spring.aop.ArithmeticCalculator.* (..))")
  public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint){

    Object result = null;
    String methodName = proceedingJoinPoint.getSignature().getName();

    try {
      //前置通知
      System.out.println("The method "+methodName+" begins with "+Arrays.asList(proceedingJoinPoint.getArgs()));
      //执行目标方法
      result = proceedingJoinPoint.proceed();

      //返回通知
      System.out.println("The method ends with "+result);
    } catch (Throwable e) {
      //异常通知
      System.out.println("The method occurs exception:"+e);

      throw new RuntimeException(e);
    }

    //后置通知
    System.out.println("The method "+methodName+" ends........");

    return result;
  }*/
}
