
package com.leysoft.configuration;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value(
            value = "${swagger.title}")
    private String title;

    @Value(
            value = "${swagger.description}")
    private String description;

    @Value(
            value = "${swagger.version}")
    private String version;

    @Value(
            value = "${swagger.contact.name}")
    private String contactName;

    @Value(
            value = "${swagger.contact.email}")
    private String contactEmail;

    @Value(
            value = "${swagger.service.name}")
    private String serviceName;

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfo(title, description, version, "",
                new Contact(contactName, "", contactEmail), "", "", new ArrayList<>());
    }

    @Bean
    public Docket docket(ApiInfo apiInfo) {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .apis(Predicates
                        .not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .apis(Predicates
                        .not(RequestHandlerSelectors.basePackage("org.springframework.cloud")))
                .paths(PathSelectors.any()).build().apiInfo(apiInfo)
                .tags(new Tag(serviceName, description));
    }
}
