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
//    @Before("execution(public * *(..))")

    public void initLog(JoinPoint joinPoint) {
        LOGGER.info("aop log {} start", joinPoint.getTarget().getClass());

        LOGGER.info("aop log:{},{},{},{},{}", joinPoint.getThis().getClass(), joinPoint.getTarget().getClass(), joinPoint.getArgs(), joinPoint.getSignature(), joinPoint.getSourceLocation());
        // com/future/controller/RestfulController 13:09:50.241 INFO  com.future.aop.BaseAopConf.initLog()/25  - aop log:class com.future.controller.RestfulController$$EnhancerBySpringCGLIB$$fba3ca3d,class com.future.controller.RestfulController,[],ResponseEntity com.future.controller.RestfulController.multiThread(),org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint$SourceLocationImpl@48b8110c




    }


}
