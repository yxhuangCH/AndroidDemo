package com.yxhuang.gintoinc.aspect;

import com.yxhuang.gintoinc.internal.DebugLog;
import com.yxhuang.gintoinc.internal.StopWatch;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by yxhuang
 * Date: 2019/5/23
 * Description:
 */
@Aspect
public class TraceAspect {

    private static final String POINTCUT_METHOD = "execution(@com.yxhuang.gintoinc.Annotation.DebugTrace * *(..))";

    private static final String POINTCUT_CONSTRUCTOR = "execution(@com.yxhuang.gintoinc.Annotation.DebugTrace *.new(..))";


    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithDebugTrace(){

    }

    @Pointcut(POINTCUT_CONSTRUCTOR)
    public void constructorAnnotatedDebugTrace(){

    }

    @Around("methodAnnotatedWithDebugTrace() || constructorAnnotatedDebugTrace()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();

        DebugLog.log(className, buildLogMessage(methodName,stopWatch.getTotalTimeMillis()));

        return result;
    }

    private String buildLogMessage(String methodName, long methodDuration){
        StringBuilder message = new StringBuilder();
        message.append("Method -->");
        message.append(methodName);
        message.append(" --> ");
        message.append("[");
        message.append(methodDuration);
        message.append("ms");
        message.append("]");

        return message.toString();
    }


}
