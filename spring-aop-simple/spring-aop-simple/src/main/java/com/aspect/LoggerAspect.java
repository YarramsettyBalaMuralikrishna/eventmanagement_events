package com.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import java.time.*;

@Aspect
@Component
public class LoggerAspect {
/*
	@Before("k()")
	public void beforeAdvice() {
		LocalTime timeNow=LocalTime.now();
		System.out.println("Before Advice invoked at "+timeNow);
	}

	@After("k()")
	public void afterAdvice() {
		LocalTime timeNow=LocalTime.now();
		System.out.println("After Advice completed at "+timeNow);
	}*/

	@Pointcut("execution(* com.service.CallerService.say* (..))")
	public void k() {
	}

	@AfterThrowing(pointcut = "k()", throwing = "ex")
	public void afterThrowingAdvice(JoinPoint joinPoint, Throwable ex) {
		System.out.println("AfterThrowing Advice: " + joinPoint.getSignature().getName() + " raised: " + ex);
	}

	@AfterReturning(pointcut = "k()", returning = "result")
	public void afterReturningAdvice(JoinPoint joinPoint, Object result) {
		System.out.println("AfterReturning Advice: " + joinPoint.getSignature().getName() + " returned: " + result);
	}

}
