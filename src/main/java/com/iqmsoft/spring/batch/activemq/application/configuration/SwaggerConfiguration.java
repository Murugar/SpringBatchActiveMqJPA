


package com.iqmsoft.spring.batch.activemq.application.configuration;

import com.google.common.base.Predicate;
import com.iqmsoft.spring.batch.activemq.application.configuration.properties.ApiInfoProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.ant;


@Configuration
@EnableSwagger2
@EnableAutoConfiguration
@EnableConfigurationProperties(ApiInfoProperties.class)
public class SwaggerConfiguration {

    @Value("${management.context-path:}")
    private String actuatorContextPath;

    @Autowired
    private ApiInfoProperties apiInfoProperties;

    @Bean
    public Docket actuatorApi(final ApiInfo apiInfo) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
//                .securityContexts()
//                .securitySchemes()
                .groupName("actuator")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(actuatorPaths())
                .build();
    }

    private Predicate<String> actuatorPaths() {
        return ant(actuatorContextPath + "/**");
    }

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(apiInfoProperties.getTitle())
                .description(apiInfoProperties.getDescription())
                .termsOfServiceUrl(apiInfoProperties.getTermsOfServiceUrl())
                .contact(apiInfoProperties.getContact())
                .license(apiInfoProperties.getLicense())
                .licenseUrl(apiInfoProperties.getLicenseUrl())
                .version(apiInfoProperties.getVersion())
                .build();
    }
}
