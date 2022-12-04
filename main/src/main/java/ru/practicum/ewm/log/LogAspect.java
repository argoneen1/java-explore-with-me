package ru.practicum.ewm.log;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.slf4j.SLF4JLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(SLF4JLogger.class);

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void callAt() {}

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

