package com.arun.rc.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Adwiti on 7/31/2018.
 */
@Aspect
@Configuration
public class LoggingConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("@annotation(com.arun.rc.config.annotation.RsLogging)")
    public void beforeAnnotation(JoinPoint joinPoint) {
        logger.info("class called {} with method {} ", joinPoint.getSignature(), joinPoint.getSignature().getName());
    }


    @Around("@annotation(com.arun.rc.config.annotation.RsTimeTraker)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        logger.info("{} executed in {} ms", joinPoint.getSignature(), executionTime);
        return proceed;
    }


}
