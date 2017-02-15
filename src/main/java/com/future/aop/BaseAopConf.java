package com.future.aop;

import com.future.controller.RestfulController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Created by zhengming on 17/2/15.
 */
@Component
@Aspect
public class BaseAopConf {

    private static Logger LOGGER = LogManager.getLogger(RestfulController.class);

    @Before("execution(* com.future.controller..*(..))")
    public void initLog(JoinPoint joinPoint) {

        LOGGER.info("aop log:{},{},{},{},{}", joinPoint.getThis().getClass(), joinPoint.getTarget().getClass(), joinPoint.getArgs(), joinPoint.getSignature(), joinPoint.getSourceLocation());
    }
}
