package com.bolsadeidead.springboot.backend.fupec.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeidead.springboot.backend.fupec.models.UsuarioCliente;
import com.bolsadeidead.springboot.backend.fupec.repositories.IUsuarioClienteRepo;

/*
 * Servicios para la comuncicacion de repositorio y controlador de UsuarioCliente
 * Leer, escribir y pasar datos
 * 
 * */

@Service
public class UsuarioClienteService {

	@Autowired
	private IUsuarioClienteRepo usuarioClienteRepo;
	
	//  crear un nuevo usuario
	@Transactional
	public UsuarioCliente crearUsuarioCliente(UsuarioCliente usuarioCliente){
		return usuarioClienteRepo.save(usuarioCliente);
	}
	
	// metodo para buscar por id
	@Transactional(readOnly = true)
	public UsuarioCliente buscarUsuarioClienteId(Long id) {
		return usuarioClienteRepo.findById(id).orElse(null);
	}
	
	// metodo para buscar por usuario
	@Transactional(readOnly = true)
	public UsuarioCliente buscarUsuarioClientePorUsuario(String usuario) {
		return usuarioClienteRepo.findByUsuario(usuario);
	}

	// metodo para buscar por nombre
	@Transactional(readOnly = true)
	public List<UsuarioCliente> listaUsuariosClientesNombre(String nombre) {
		return usuarioClienteRepo.findByNombreContainingIgnoreCase(nombre);
	}

	// metodo para buscar por nombre usuario
	@Transactional(readOnly = true)
	public List<UsuarioCliente> listaUsuariosClientesUsuario(String usuario) {
		return usuarioClienteRepo.findByUsuarioContainingIgnoreCase(usuario);
	}
	
	// para eliminar el usuario
	@Transactional
	public void eliminarUsuarioCliente(Long id) {
		usuarioClienteRepo.deleteById(id);
	}

}
