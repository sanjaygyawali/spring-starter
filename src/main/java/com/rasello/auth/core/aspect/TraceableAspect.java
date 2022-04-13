package com.rasello.auth.core.aspect;

import com.rasello.auth.core.services.SeedReadWriteService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class TraceableAspect {

    private SeedReadWriteService seedReadWriteService;

    public TraceableAspect(SeedReadWriteService seedReadWriteService) {
        this.seedReadWriteService = seedReadWriteService;
    }

    @AfterReturning( pointcut = "@annotation(com.rasello.auth.core.annotation.Traceable)", returning = "result")
    private  void log(JoinPoint joinPoint, Object result) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if(method.getName().equals("save")){
            seedReadWriteService.write(result.getClass().getSimpleName(),result);
        }
        log.debug("I have reached here");
    }

//    @Around( value = "@annotation(com.rasello.auth.core.annotation.Traceable)")
//    private void logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
//        String methodName = proceedingJoinPoint.getSignature().getName();
//    }

}
