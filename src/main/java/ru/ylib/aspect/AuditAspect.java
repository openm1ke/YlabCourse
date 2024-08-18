package ru.ylib.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {

    private static final Logger log = LoggerFactory.getLogger(AuditAspect.class);

    @Around("execution(* ru.ylib.services.*.*(..))")
    public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {
        // Логирование информации перед выполнением метода
        log.info("Calling method: {}", joinPoint.getSignature());

        // Получение времени начала выполнения метода
        long startTime = System.currentTimeMillis();

        // Выполнение целевого метода
        Object result = joinPoint.proceed();

        // Логирование информации после выполнения метода
        long elapsedTime = System.currentTimeMillis() - startTime;
        log.info("Method {} executed in {} ms", joinPoint.getSignature(), elapsedTime);

        return result;
    }
}
