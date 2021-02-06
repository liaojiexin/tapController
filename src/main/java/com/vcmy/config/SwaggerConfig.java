package com.vcmy.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @ClassName: SwaggerConfig.java
 * @Description:  swagger2配置
 * @version: 1.0.0
 * @author Rock
 * @date: 2018年5月8日 上午10:58:08
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
					.globalOperationParameters(setHeaderToken())
					.apiInfo(apiInfo())
					.select()
					.apis(RequestHandlerSelectors.basePackage("com.vcmy.controller"))
					.paths(PathSelectors.any())
					.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("RESTful APIs")
				.description("更多相关内容请关注：http://www.vcmy.com/")
				.termsOfServiceUrl("http://www.vcmy.com/")
				.contact(new Contact("vcmy", "", ""))
				.version("1.0")
				.build();
	}
	
	private List<Parameter> setHeaderToken() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("X-Auth-Token")
        	.description("token")
        	.modelRef(new ModelRef("string"))
        	.parameterType("header")
        	.required(false)
        	.build();
        pars.add(tokenPar.build());
        return pars;
    }

}
