package com.bolsadeidead.springboot.backend.fupec.auth;

import java.util.Arrays;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/*
 * Para dar accesos a las del back  a los usuarios segun su rol
 * Configuracion de cors
 * */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "favicon.ico" ).permitAll()
			// solo user y admin cruds
			.antMatchers("/api/cliente/**").hasAnyRole("User","Admin")
			.antMatchers("/api/usucli/**").hasAnyRole("User","Admin")
			.antMatchers("/api/seguimiento/**").hasAnyRole("User","Admin")
			// solo user y admin gets
			.antMatchers(HttpMethod.GET,"/api/interes/**").hasAnyRole("User","Admin")
			.antMatchers(HttpMethod.GET,"/api/intereses").hasAnyRole("User","Admin")
			.antMatchers(HttpMethod.GET,"/api/tarjeta/**").hasAnyRole("User","Admin")
			.antMatchers(HttpMethod.GET,"/api/vendedor/**").hasAnyRole("User","Admin")
			// solo admin
			.antMatchers("/api/interes/**").hasRole("Admin")
			.antMatchers("/api/tarjeta/**").hasRole("Admin")
			.antMatchers("/api/vendedor/**").hasRole("Admin")
			// todo requiere autentificacion
			.anyRequest().authenticated()
			.and().cors().configurationSource(corsConfigurationSource());// pasamos la metodo configuracion cors
	}
	
	// Configuracion Cors para informacion cruzada
	@Bean
	public CorsConfigurationSource corsConfigurationSource () {
		CorsConfiguration configuracion = new CorsConfiguration();
		configuracion.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://192.168.1.8:4200")); // permitir el dominio del cliente "angular"
		configuracion.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS")); // configurar todos los verbos, a usar en el back
		configuracion.setAllowCredentials(true); // permitimos credenciales
		configuracion.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization")); // permitimos als cabeceras
		
		// registramos configuracion del cors para todas las rutas del back
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuracion);
		
		return source;
	}
	
	// filtro de cors y pasamos toda la configuracion anterior
	@Bean 
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>( new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE); // prioridad alta 
		return bean;
	}
}
