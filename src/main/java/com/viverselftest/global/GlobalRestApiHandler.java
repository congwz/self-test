package com.viverselftest.global;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viverselftest.common.ServerResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@ControllerAdvice(basePackages = "com.viverselftest.api")
public class GlobalRestApiHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            if (body instanceof String) {
                return objectMapper.writeValueAsString(ServerResponse.successData(body)); //返回的数据是json格式
                //return objectMapper.writeValueAsString(body);  //返回的数据直接是String
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ServerResponse.successData(body);
    }
}
