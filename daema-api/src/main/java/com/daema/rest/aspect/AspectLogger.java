package com.daema.rest.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class AspectLogger {

    private static final Logger logger = LoggerFactory.getLogger(AspectLogger.class);

    @Pointcut("execution(* com..*Controller.*(..))")
    private void targetMethod(){

    }

    @Around("targetMethod()")
    public Object logginAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorization = request.getHeader("Authorization");

        long startTime = System.currentTimeMillis();
        Object[] objs = proceedingJoinPoint.getArgs();

        StringBuffer argsToString = new StringBuffer();

        argsToString.append("authorization_").append(authorization).append(" : ");

        if(objs != null){
            for(int i = 0; i < objs.length; i++){
                if(objs[i] != null){
                    if(i < objs.length - 1) {
                        argsToString.append("'").append(objs[i].toString()).append("',");
                    } else {
                        argsToString.append("'").append(objs[i].toString()).append("'");
                    }
                }
            }
        }

        String sessionId = null;
        Object output;

        if (RequestContextHolder.getRequestAttributes() != null) {
            sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
        }

        if (sessionId != null) {
            StringBuffer enterLog = new StringBuffer();
            enterLog.append("Enter : ").append(proceedingJoinPoint.getSignature());
            enterLog.append(" with args(").append(argsToString).append(")");
            enterLog.append(". Session ID: ").append(RequestContextHolder.getRequestAttributes().getSessionId());

            logger.info(enterLog.toString());
            output = proceedingJoinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - startTime;

            StringBuffer exitLog = new StringBuffer();
            exitLog.append("Exit : ").append(proceedingJoinPoint.getSignature());
            exitLog.append(", Execution time : ").append(elapsedTime).append(" milliseconds. return : ");
            exitLog.append("authorization_").append(authorization).append(" : ");
            exitLog.append(((output != null) ? output.toString() : "")).append(". Session ID: ").append(RequestContextHolder.getRequestAttributes().getSessionId());

            logger.info(exitLog.toString());

        } else {
            output = proceedingJoinPoint.proceed();
        }

        return output;
    }

    @AfterThrowing(pointcut = "targetMethod()", throwing = "exception")
    public void afterThrowingTargetMethod(JoinPoint joinPoint, Exception exception) {
        StringBuffer throwingLog = new StringBuffer();
        throwingLog.append("C @AfterThrowing Exiting: ").append(joinPoint.getSignature());
        throwingLog.append(". Session ID: ").append(RequestContextHolder.getRequestAttributes().getSessionId());

        logger.info(throwingLog.toString());
    }
}
