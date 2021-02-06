package com.vcmy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@EnableScheduling     //启动定时器
@SpringBootApplication
@EnableTransactionManagement        //启动事务
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@EnableAspectJAutoProxy(proxyTargetClass=true)
@MapperScan("com.vcmy.dao")
@EnableSwagger2     //使用Swagger
public class VcmyControllerApplication {
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(VcmyControllerApplication.class);
		springApplication.setBannerMode(Banner.Mode.OFF); 
		springApplication.run(args);
	}
	
	@Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> containerCustomizer() {

        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
 
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
 
                factory.addErrorPages(error404Page);
            }	
        };
    }
}
