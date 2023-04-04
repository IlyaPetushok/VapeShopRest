package project.vapeshop.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Aspect
@Configuration
public class LogAspect {

    @Around(value = "project.vapeshop.aop.CustomPointcuts.mainPointcut()")
    public Object loggerAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final Logger logger= LogManager.getLogger(proceedingJoinPoint.getTarget().getClass());
        String className=proceedingJoinPoint.getTarget().getClass().getName();
        String methodName=((MethodSignature)proceedingJoinPoint.getSignature()).getMethod().getName();
        long startTime=System.nanoTime();
        String methodArgs= Stream.of(proceedingJoinPoint.getArgs()).collect(Collectors.toList()).toString();
        Object result=proceedingJoinPoint.proceed();
        long endTime=System.nanoTime();
        long elapsedTime=endTime-startTime;
        LogMessage logMessage=LogMessage.builder()
                .className(className)
                .methodName(methodName)
                .methodArgs(methodArgs)
                .elapsedTimeInMillis(TimeUnit.NANOSECONDS.toMillis(elapsedTime))
                .elapsedTimeInMicros(TimeUnit.NANOSECONDS.toMicros(elapsedTime))
                .result(result)
                .build();
        logger.info("Logger:{}",logMessage);
        return result;
    }
}








