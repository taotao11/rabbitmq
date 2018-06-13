package com.boot.rabbitmq.reg.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * swagger2 配置
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api") //定义组
                .apiInfo(apiInfo())//配置说明
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.boot.rabbitmq.reg.controller"))//拦截包路径
                .paths(PathSelectors.any()) //拦截接口
                .build();
    }
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("rabbitmq 邮件发送")
                .description("邮件发送")
                .termsOfServiceUrl("920518289@qq.com")
                .version("1.0")
                .build();
    }
}
