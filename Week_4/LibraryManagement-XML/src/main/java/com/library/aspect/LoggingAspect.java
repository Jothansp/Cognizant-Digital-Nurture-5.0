package com.library.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Ex 3: @Around advice logs method execution time.
 * Ex 8: @Before and @After advice log entry/exit (cross-cutting concern).
 */
@Aspect
public class LoggingAspect {

    @Before("execution(* com.library.service.*.*(..))")
    public void logBefore(JoinPoint jp) {
        System.out.println("[AOP][Before] " + jp.getSignature().toShortString());
    }

    @After("execution(* com.library.service.*.*(..))")
    public void logAfter(JoinPoint jp) {
        System.out.println("[AOP][After ] " + jp.getSignature().toShortString());
    }

    @Around("execution(* com.library.service.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.nanoTime();
        Object result = pjp.proceed();
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;
        System.out.println("[AOP][Time  ] " + pjp.getSignature().toShortString()
                + " -> " + elapsedMs + " ms");
        return result;
    }
}
