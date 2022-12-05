package ru.practicum.ewm.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void callAt() {
    }

    @Before("callAt()")
    public void before(JoinPoint pjp) {
        log.info(
                "{}.{} executed with {}",
                pjp.getSourceLocation().getWithinType().getSimpleName(),
                pjp.getStaticPart().getSignature().getName(),
                pjp.getArgs()
        );
    }
}

