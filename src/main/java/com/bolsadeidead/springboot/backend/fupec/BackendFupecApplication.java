package com.bolsadeidead.springboot.backend.fupec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class BackendFupecApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(BackendFupecApplication.class, args);
		
	}
	// creacion para el war
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(BackendFupecApplication.class);
	}
	
}
