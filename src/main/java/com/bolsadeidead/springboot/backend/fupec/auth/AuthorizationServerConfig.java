package com.bolsadeidead.springboot.backend.fupec.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/*
 * Configuracion el servidor de authorizacion
 * Configuracion nombre cliente
 * Configuracion contraseña
 * Configuracion Token "validacion, firma, tiempo"
 * */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private InfomationAdditionalToken informacionAdicionalToken;
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()") // permite a cualquier usuario el logeo
				.checkTokenAccess("isAuthenticated()"); // confirma si es valido el token del usaurio autentificado
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			   .withClient("fupecApp") // nombre del cliente "frontEnd"
			   .secret(passwordEncoder.encode("FuPeC@2021#")) // contraseña para el cliente "frontend"
			   .scopes("read", "write") // permite leer y escribir a cliente
			   .authorizedGrantTypes("password", "refresh_token") // para autorizar por contraseña y autorizar refrescar el token
			   .accessTokenValiditySeconds(36000) // tiempo de valides del token 10H
			   .refreshTokenValiditySeconds(36000); // tiempo de valides del token renovado 10H
			   
	}
	
	// proceso de autentificacion y validar token
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// unimos informacion por defecto del token con la nueva adicional
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(informacionAdicionalToken, accessTokenConverter()));
		
		// 
		endpoints.authenticationManager(authenticationManager)
				 .accessTokenConverter(accessTokenConverter())
				 .tokenEnhancer(tokenEnhancerChain);
	}
	
	// Firmar token RSA
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey(JwtConfig.RSA_PRIVADA);// para firmar el token
		jwtAccessTokenConverter.setVerifierKey(JwtConfig.RSA_PUBLICA);// verificar el token
		return jwtAccessTokenConverter;
	}
	
	
}
