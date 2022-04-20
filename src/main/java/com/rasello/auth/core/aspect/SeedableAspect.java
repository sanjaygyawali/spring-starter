package com.rasello.auth.core.aspect;

import com.rasello.auth.core.services.MetaDataReadWriteService;
import com.rasello.auth.core.services.entity.BaseEntity;
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
public class SeedableAspect {

    private MetaDataReadWriteService seedReadWriteService;

    public SeedableAspect(MetaDataReadWriteService seedReadWriteService) {
        this.seedReadWriteService = seedReadWriteService;
    }

    @AfterReturning( pointcut = "@annotation(com.rasello.auth.core.annotation.Seedable)", returning = "result")
    private  void log(JoinPoint joinPoint, Object result) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if(method.getName().equals("save") || method.getName().equals("save")){
            seedReadWriteService.write(result.getClass().getSimpleName(),(BaseEntity) result);
            log.debug("written to seed");
        }
    }

//    @Around( value = "@annotation(com.rasello.auth.core.annotation.Seedable)")
//    private void logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
//        String methodName = proceedingJoinPoint.getSignature().getName();
//    }

}
