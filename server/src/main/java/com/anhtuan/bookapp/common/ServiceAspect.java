package com.anhtuan.bookapp.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class ServiceAspect {

    private Logger logger = LoggerFactory.getLogger(ServiceAspect.class);

    @Around("@annotation(com.anhtuan.bookapp.common.CheckMethod)")
    public void aroundCheckMethod(ProceedingJoinPoint joinPoint) throws Throwable{
        logger.info("Start: {}", joinPoint);
        joinPoint.proceed();
        logger.info("Done: {}", joinPoint);
    }

}
