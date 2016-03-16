package com.mrlu.sven.controller.aspect;

import com.mrlu.sven.common.HandleModelResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
/**
 * Created by stefan on 16-1-25.
 */
@Aspect
public class ControllerAspect {
    @Around("execution(* com.mrlu.sven.controller.*.*(..)) && !@annotation(NotAspect)")
    public Object doAround(ProceedingJoinPoint pjp){
        try {
            Object result = pjp.proceed();
            return HandleModelResult.returnOkModelResult(result);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return HandleModelResult.returnExceptionModelResult(throwable);
        }
    }
}
