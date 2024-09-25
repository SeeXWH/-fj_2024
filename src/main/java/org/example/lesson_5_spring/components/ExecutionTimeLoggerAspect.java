package org.example.lesson_5_spring.components;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j()
public class ExecutionTimeLoggerAspect {
    @Around("@within(org.example.lesson_5_spring.annotation.LogExecutionTime) || @annotation(org.example.lesson_5_spring.annotation.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        log.info("Method {}.{} executed in {} ms", className, methodName, executionTime);
        return proceed;
    }
}
