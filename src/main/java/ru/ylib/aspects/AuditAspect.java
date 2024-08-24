package ru.ylib.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {

    @Around("execution(* ru.ylib.services.*.*(..))")
    public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {
        // Логирование информации перед выполнением метода

        // Получение времени начала выполнения метода
        long startTime = System.currentTimeMillis();

        // Выполнение целевого метода
        Object result = joinPoint.proceed();

        // Логирование информации после выполнения метода
        long elapsedTime = System.currentTimeMillis() - startTime;

        return result;
    }
}
