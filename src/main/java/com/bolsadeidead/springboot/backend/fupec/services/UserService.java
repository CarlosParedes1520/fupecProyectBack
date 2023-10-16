package com.bolsadeidead.springboot.backend.fupec.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.bolsadeidead.springboot.backend.fupec.models.UserAuth;
import com.bolsadeidead.springboot.backend.fupec.repositories.IUserRepo;

/*
 * Servicios para la comuncicacion de repositorio y controlador de User
 * Leer, escribir y pasar datos
 * 
 * */

@Service
public class UserService implements UserDetailsService {
	
	// mostrar informacion consola
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private IUserRepo userRepo;
	
	// buscar user por nombre
	@Transactional(readOnly = true)
	public UserAuth findByUsername(String username) {
		return userRepo.findByUsernameAndStatus(username, 1);
	}
	
	// buscar user por id
	@Transactional(readOnly = true)
	public UserAuth findById(Long id) {
		return userRepo.findByIdAndStatus(id, 1);
	}
	
	// usuario a verificar autentificacion
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// busco user por nombre
		UserAuth usuario = userRepo.findByUsernameAndStatus(username, 1);
		// array para los roles
		List<GrantedAuthority> authorities = new ArrayList<>();
		// validacion
		if ( usuario == null ) {
			logger.error("Error en el login : No existe el usuario: "+ username + " en el sistema.");
			throw new UsernameNotFoundException("Error en el login : No existe el usuario: "+ username + " en el sistema.");
		}
		// para saber el estado del usuario
		Boolean usuarioStatus = false;
		if ( usuario.getStatus() == 1 ) usuarioStatus = true;
		
		// agregamos los roles al array
		authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getUserLevel().getGroupName()));
		// para cifrar la contraseña del usuario
		BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
		// pasamos el nuevo cifrado al usuario
		usuario.setPassword(passEncoder.encode(usuario.getPassword()));
		return new User(usuario.getUsername(), usuario.getPassword(), usuarioStatus , true, true, true, authorities);
	}

}
