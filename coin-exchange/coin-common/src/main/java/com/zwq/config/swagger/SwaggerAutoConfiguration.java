package com.zwq.config.swagger;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.Arrays;
import java.util.List;

/**
 * @Author Acer
 * @Date 2021/08/22 13:57
 * @Version 1.0
 */
@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfiguration {

    private SwaggerProperties swaggerProperties;

    public SwaggerAutoConfiguration(SwaggerProperties swaggerProperties){
        this.swaggerProperties = swaggerProperties;
    }
    public Docket docket(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zwq.controller"))
                .paths(PathSelectors.any())
                .build();

                docket().securitySchemes(securitySchemes())
                        .securityContexts(securityContext());
                return docket;
    }

    private List<SecurityScheme> securitySchemes() {
        return Arrays.asList(new ApiKey("Authorization","Authorization","Authorization"));
    }

    private List<SecurityContext> securityContext() {
        return Arrays.asList(new SecurityContext(
                Arrays.asList(new SecurityReference("Authorization",new AuthorizationScope[]{new AuthorizationScope("global","accessResource")})),
                        PathSelectors.any()
                )
        );
    }

    /**
     * api简介
     * @return
     */
    private ApiInfo apiInfo() {
        new ApiInfoBuilder().contact(
                new Contact(swaggerProperties.getName(),swaggerProperties.getUrl(),swaggerProperties.getEmail())
        )
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .build();
    }


}
