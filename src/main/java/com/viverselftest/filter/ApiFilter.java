package com.viverselftest.filter;


import com.github.pagehelper.StringUtil;
import com.viverselftest.exception.ErrorException;
import com.viverselftest.util.ErrorMsgUtils;
import com.viverselftest.util.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

//@Configuration
//public class ApiFilter extends WebMvcConfigurerAdapter {
public class ApiFilter {

    //@Autowired
    //private ObjectMapper objectMapper;

    @Autowired
    private ErrorMsgUtils errorMsgUtils;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${passport.api.url}")
    private String passportUrl;

    private Logger logger = LoggerFactory.getLogger(ApiFilter.class);

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptorAdapter() {

            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

                logger.info("请求执行:" + request.getRequestURL());

                String token = StrUtils.trim(request.getHeader("token"));
                token = StrUtils.isEmpty(token) ? StrUtils.trim(request.getParameter("token")) : token;

                String system_language = StrUtils.trim(request.getHeader("locale"));
                system_language = StrUtils.isEmpty(system_language) ? StrUtils.trim(request.getParameter("locale")) : system_language;

                System.out.println(new Date() + " token :" + token + "    system_language :" + system_language);

                logger.info("token :" + token + "    system_language :" + system_language);

                if (StringUtil.isEmpty(token)) {
                    throw new ErrorException("100000",errorMsgUtils.errorMsg(system_language,"100000"));
                }

                String url = passportUrl + "/redis/check/" + token;
                Map<String,Object> retMap = new RestTemplate().getForObject(url,Map.class);
                if (!(boolean) retMap.get("data")) {
                    throw new ErrorException("100000",errorMsgUtils.errorMsg(system_language,"100000"));
                }

                return true;
            }

        }).addPathPatterns("/api/**");

    }

    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }*/
}
