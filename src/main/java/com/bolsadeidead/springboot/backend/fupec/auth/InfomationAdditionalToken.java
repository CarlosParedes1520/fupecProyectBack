package com.bolsadeidead.springboot.backend.fupec.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.bolsadeidead.springboot.backend.fupec.models.UserAuth;
import com.bolsadeidead.springboot.backend.fupec.services.UserService;

/*
 * 
 * Agregar informacion adicional al token
 * 
 * */
@Component
public class InfomationAdditionalToken implements TokenEnhancer {
	
	@Autowired
	private UserService userService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		// buscar por usename user
		UserAuth usuario = userService.findByUsername(authentication.getName());
		// Hasmap para pasar informacion adicional al token
		Map<String, Object> informacion = new HashMap<>();
		informacion.put("id", usuario.getId());
		informacion.put("name", usuario.getName());
		// castemos accessToken de tipo DefaulOAuth2... para llamar el metodo setAditional...
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(informacion);
		return accessToken;
	}

}