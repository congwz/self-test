package com.viverselftest.config;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class Swagger {

    @Bean
    public Docket swaggerConfig(){
        /*return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(newArrayList(
                        new ParameterBuilder()
                        .name("language")
                        .description("语言")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(false)
                        .build(),

                        new ParameterBuilder()
                        .name("token")
                        .description("token")
                        .modelRef(new ModelRef("String"))
                        .parameterType("header")
                        .required(false)
                        .build()

                 ))
                .groupName("Viver-Self-Test")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(false)
                .pathMapping("")
                .select()
                .paths(PathSelectors.regex("/api/.*"))
                .build()
                .apiInfo(viverApiInfo());*/
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .produces(Sets.newHashSet("application/json;charset=UTF-8"))
                .groupName("Viver-Self-Test")
                //.useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.viverselftest.api"))
                //.paths(PathSelectors.regex("/api/.*"))
                .build()
                .apiInfo(viverApiInfo());
    }

    private ApiInfo viverApiInfo(){
        ApiInfo apiInfo = new ApiInfo(
                "自己测试-project 接口",
                "test restful api",
                "1.0",
                "www.baidu.com",
                "Viver",
                "Swagger官网",
                "https://swagger.io/docs/"
        );
        return apiInfo;
    }


}
