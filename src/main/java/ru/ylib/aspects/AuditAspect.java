package ru.ylib.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AuditAspect {

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
