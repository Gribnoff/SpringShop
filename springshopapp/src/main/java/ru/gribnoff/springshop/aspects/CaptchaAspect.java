package ru.gribnoff.springshop.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class CaptchaAspect {
	@Pointcut("execution(public * ru.gribnoff.springshop.util.CaptchaGenerator.*(..))")
	public void callAtCaptchaGeneratorPublic() {}

	@After("callAtCaptchaGeneratorPublic()")
	public void logAfterCallAt(JoinPoint joinPoint) {
		log.info("{}: {}", joinPoint.getKind(), joinPoint.getSignature());
	}
}
