package com.viverselftest.common;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Congwz on 2018/6/4.
 */
@Component
@Aspect
public class ApiLogAOP {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 公用的切入点
     */
    @Pointcut("execution(public * com.viverselftest.api.*.*(..))")
    public void log() {
    }

    /**
     * api接口执行之前-输出参数信息到log中
     * @param joinPoint
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        //logger.info("接口方法执行前...");
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        logger.info("url: " + request.getRequestURI());
        //logger.info("ip:" + request.getRemoteHost());
        //logger.info("method:" + request.getMethod());
        logger.info("class_method: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        String args = " ";
        for(Object o : joinPoint.getArgs()){
            //logger.info("args: " + o.getClass() + "  " + o.toString());
            args += o.toString() + " ";
        }
        if(!StringUtils.isEmpty(args.trim())){
            logger.info("args: " + args.trim());
        }

    }
}
