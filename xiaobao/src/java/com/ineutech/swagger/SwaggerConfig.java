package com.ineutech.swagger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
@ComponentScan(basePackages = {"com.ineutech.control.api"})
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
    	
        return new Docket(DocumentationType.SWAGGER_2)   //主要api配置机制初始化为swagger规范2.0
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ineutech.control.api"))
//                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("XIAOBAO API")  // 标题
                .description("XIAOBAO APP 接口")  // 描述信息
                .termsOfServiceUrl("http://localhost:8091/xiaobao")  //服务网址
//                .contact(new Contact("露露","","543979100@qq.com"))  // 联系方式
                .version("1.0") //版本号
                .build();
    }
}
