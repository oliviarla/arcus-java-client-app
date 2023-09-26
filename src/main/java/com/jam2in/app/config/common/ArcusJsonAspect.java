package com.jam2in.app.config.common;

import com.jam2in.arcus.app.common.aop.ArcusCacheJsonAspect;
import com.jam2in.arcus.app.common.config.ArcusConfiguration;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ArcusJsonAspect extends ArcusCacheJsonAspect {
	public ArcusJsonAspect(ArcusConfiguration configuration) {
		super(configuration);
	}

	@Pointcut("execution(!void com.jam2in.app.feed.service.*Service.get*(..))")
	public void pointcut() {
	}

	@Override
	@Around("pointcut()")
	public Object around(final ProceedingJoinPoint joinPoint) throws Throwable {
		return super.around(joinPoint);
	}
}
