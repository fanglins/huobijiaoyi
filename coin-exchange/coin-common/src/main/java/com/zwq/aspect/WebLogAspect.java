package com.zwq.aspect;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.zwq.model.WebLog;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.apache.ibatis.binding.MapperMethod;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @Author Acer
 * @Date 2021/08/22 14:45
 * @Version 1.0
 */
@Component
@Aspect
@Order(1)
public class WebLogAspect {

    @Pointcut("execution(* com.zwq.controller.*.*(..))")
    public void webLog(){}

    @Around("webLog()")
    public Object recodeWebLogb(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        WebLog webLog = new WebLog();
        long start = System.currentTimeMillis();

        result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        long end = System.currentTimeMillis();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String url = request.getRequestURL().toString();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        webLog.setSpendTime((int)(start-end)/1000);
        webLog.setUri(request.getRequestURI());
        webLog.setUrl(url);
        webLog.setBasePath(StrUtil.removeSuffix(url, URLUtil.url(url).getPath()));
        webLog.setUsername(authentication==null ? "anonymous":authentication.getPrincipal().toString());
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();

        String targetClassName = proceedingJoinPoint.getTarget().getClass().getName();
        Method method = signature.getMethod();
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);
        webLog.setIp(request.getRemoteAddr());
        webLog.setDescription(annotation==null ? "no desc":annotation.value());
        webLog.setMethod(targetClassName+"."+method.getName());
        webLog.setParameter(getMethodParameter(method,proceedingJoinPoint.getArgs()));
        webLog.setResult(result);
        return result;


    }

    private Object getMethodParameter(Method method,Object[] args) {
        HashMap<String,Object> methodParamterWithValue = new HashMap<>();
        LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = localVariableTableParameterNameDiscoverer.getParameterNames(method);
        for (int i = 0;i<parameterNames.length;i++){
            methodParamterWithValue.put(parameterNames[i],args[i]);
        }

        return methodParamterWithValue;
    }

}
