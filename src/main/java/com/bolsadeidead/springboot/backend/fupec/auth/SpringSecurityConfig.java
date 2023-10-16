package com.bolsadeidead.springboot.backend.fupec.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/*
 * 
 * Configuracion de seguridad del springboot
 * 
 * */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// Pasamos userDatails y el tipo de cifrado a usar
	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userService).passwordEncoder(passwordEncoder());
		super.configure(auth);
	}
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	// configuracion http 
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.anyRequest().authenticated() // todas las rutas necesitan autentificacion
			.and()
			.csrf().disable() // deshabilitamos los formularios cruzados
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // deshabilitamos las sesiones
	}
	
	
}
