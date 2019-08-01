package com.viverselftest.common;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Haoxu.Mu on 2018/11/22.
 *
 */
@Aspect
@Component
public class ApiLogAspectBetter {

    private final static Logger logger = LoggerFactory.getLogger(ApiLogAspectBetter.class);

    /**
     * 定义切点 AOP扫描路径
     *
     */
    @Pointcut("execution(public * io.harmontronics.opendevices.controller..*.*(..))")
    public void log(){}


    /**
     * 前置通知 记录HTTP请求开始时的日志
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //URL
        logger.info("  【start time: {}】, url = {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), request.getRequestURI());
        //method
        logger.info("  method = {}", request.getMethod());
        //ip
        logger.info("  ip = {}",request.getRemoteAddr());
        //类方法
        logger.info("  class = {}  and  method name = {}",joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName());
        //参数
        logger.info("  参数 = {}",joinPoint.getArgs());
    }

    /**
     * 后置通知 记录HTTP请求结束时的日志
     */
    @After("log()")
    public void doAfter(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("  url = {} end of execution", request.getRequestURL());
    }

    /**
     * 获取返回内容
     * @param object
     */
    @AfterReturning(returning = "object",pointcut = "log()")
    public void doAfterReturn(Object object) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        if (object  == null) {
            logger.info("  【end time: {}】, url = {}, response = {}",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), request.getRequestURL(), "object is null");
        } else {

            //logger.info("  response = {}", ToStringBuilder.reflectionToString(object, ToStringStyle.JSON_STYLE));
            //logger.info("  response = {}",object.toString());
            //logger.info("  response = {}", net.sf.json.JSONObject.fromObject(object).toString());

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                logger.info("  【end time: {}】, url = {}, response = {}",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), request.getRequestURL(), objectMapper.writeValueAsString(ServerResponse.successData(object)));
            } catch (JsonProcessingException e) {
                System.out.println("aop afterReturning throw exception!");
                e.printStackTrace();
            }
        }
    }

}