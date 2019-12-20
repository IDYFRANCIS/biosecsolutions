package com.francis.biosectest;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.francis.biosectest.constants.AppConstants;

//import com.bizzdesk.inventory.constant.AppConstants;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	
	@Autowired
    private AppConstants appConstants;

		 @Bean
		    public Docket api() {
		        return new Docket(DocumentationType.SWAGGER_2).select()
		            .apis(RequestHandlerSelectors.basePackage("com.francis.biosectest.controller"))
		            .paths(PathSelectors.any())
		            .build()
		            .apiInfo(apiInfo());
		    }

		 private ApiInfo apiInfo() {
		        return new ApiInfo(appConstants.APP_NAME, appConstants.APP_DESCRIPTION, appConstants.APP_VERSION, "Terms of service", new Contact(appConstants.APP_AUTHOR, appConstants.APP_URL, appConstants.APP_EMAIL), "API License", appConstants.APP_LICENSE_URL,
		            Collections.emptyList());
		    }
 }
