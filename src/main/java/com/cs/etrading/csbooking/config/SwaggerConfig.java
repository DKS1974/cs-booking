package com.cs.etrading.csbooking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket bookApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Books")
                .apiInfo(bookApiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/book/**"))
                .build();
    }

    @Bean
    public Docket orderApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Order")
                .apiInfo(bidApiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/order/**"))
                .build();
    }

    @Bean
    public Docket distributionApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Distribution")
                .apiInfo(distributionApiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/distribution/**"))
                .build();
    }

    private ApiInfo bookApiInfo() {
        return new ApiInfoBuilder()
                .title("Books Api")
                .description("Book Api definition")
                .build();
    }

    private ApiInfo bidApiInfo() {
        return new ApiInfoBuilder()
                .title("Order Api")
                .description("Order Api definition")
                .build();
    }

    private ApiInfo distributionApiInfo() {
        return new ApiInfoBuilder()
                .title("Distribution Api")
                .description("Distribution Api definition")
                .build();
    }
}