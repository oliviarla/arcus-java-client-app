package com.jam2in.app.config.common;

import com.jam2in.arcus.app.common.aop.ArcusCacheAspect;
import com.jam2in.arcus.app.common.config.ArcusConfiguration;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ArcusAnnotationAspect extends ArcusCacheAspect {
	public ArcusAnnotationAspect(ArcusConfiguration configuration) {
		super(configuration);
	}

	@Pointcut("@annotation(com.jam2in.arcus.app.common.aop.ArcusCache) && execution(!void com.jam2in.app.feed..*(..))")
	public void pointcut() {
	}

	@Override
	@Around("pointcut()")
	public Object around(final ProceedingJoinPoint joinPoint) throws Throwable {
		return super.around(joinPoint);
	}
}
