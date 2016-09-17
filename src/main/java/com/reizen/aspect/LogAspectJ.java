package com.reizen.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

public class LogAspectJ {

	public LogAspectJ() {
		System.out.println(":: LogAspectJ Default Constructor");
	}
	
	public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable{
    System.out.println("[Around before] "+getClass()+".invoke() start....");
    System.out.println("[Around before] 타겟 Instance : "+joinPoint.getTarget().getClass().getName());
    System.out.println("[Around before] 타겟 Instance의 호출 될 method : "+joinPoint.getSignature().getName());
    if (joinPoint.getArgs().length != 0) {
      System.out.println("[Around before] 타겟 Instance의 호출할 method에 전달되는 인자 : "+joinPoint.getArgs()[0]);
    }
    Object obj = joinPoint.proceed();

    System.out.println("[Around after] 타겟 Instance 호출후 return value : "+obj);
    System.out.println("[Around after] "+getClass()+".invoke() end....");
    
    return obj;
  }
	
}
