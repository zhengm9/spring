package com.future.aop;

import com.future.annotation.LoginValidation;
import com.future.controller.RestfulController;
import com.future.filter.SessionRecorder;
import com.google.common.base.Strings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by zhengming on 17/2/15.
 */
@Component
@Aspect
public class BaseAopConf {

    private static Logger LOGGER = LogManager.getLogger(RestfulController.class);

    @Before("execution(* com.future.controller..*(..))")
//    @Before("execution(public * *(..))")

    public void initLog(JoinPoint joinPoint) {
        LOGGER.info("aop log {} start", joinPoint.getTarget().getClass());
        if(((MethodSignature)joinPoint.getSignature()).getMethod().isAnnotationPresent(LoginValidation.class))
        {
            LOGGER.info("LoginValidation annotation info:{}", ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(LoginValidation.class).value());
        }

        LOGGER.info("aop log:{},{},{},{},{}", joinPoint.getThis().getClass(), joinPoint.getTarget().getClass(), joinPoint.getArgs(), joinPoint.getSignature(), joinPoint.getSourceLocation());
        // com/future/controller/RestfulController 13:09:50.241 INFO  com.future.aop.BaseAopConf.initLog()/25  - aop log:class com.future.controller.RestfulController$$EnhancerBySpringCGLIB$$fba3ca3d,class com.future.controller.RestfulController,[],ResponseEntity com.future.controller.RestfulController.multiThread(),org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint$SourceLocationImpl@48b8110c




    }

    @Before("@annotation(com.future.annotation.LoginValidation)")
    public void  loginValidate(JoinPoint joinPoint)
    {
        LOGGER.info("LoginValidation annotation tested!session:{}",SessionRecorder.getRequest().getSession().getAttribute("user"));

        if(SessionRecorder.getRequest().getSession().getAttribute("username") == null)
        {
            LOGGER.info("user not log in!");
            try {
                SessionRecorder.getRequest().getRequestDispatcher("login").forward(SessionRecorder.getRequest(), SessionRecorder.getResponse());
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
