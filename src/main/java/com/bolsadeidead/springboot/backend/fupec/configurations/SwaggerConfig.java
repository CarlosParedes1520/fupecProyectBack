package com.bolsadeidead.springboot.backend.fupec.configurations;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
 * Configuracion para la documentacion del api realizada usando swagger 2
 * */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.bolsadeidead.springboot.backend.fupec.controllers"))
				.paths(PathSelectors.any())
				.build();
	}
	
}
