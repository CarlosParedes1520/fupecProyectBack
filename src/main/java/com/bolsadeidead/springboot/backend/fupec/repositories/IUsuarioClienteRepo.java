package com.bolsadeidead.springboot.backend.fupec.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bolsadeidead.springboot.backend.fupec.models.UsuarioCliente;

/*
 * Repositorio jpa Dao de UsuarioCliente
 * configuracion apra las consultas a la base de datos
 * 
 * */

@Repository
public interface IUsuarioClienteRepo extends JpaRepository<UsuarioCliente, Long> {
	
	// consulta para buscar por nombre completo o solo algunos caracteres
	public List<UsuarioCliente> findByNombreContainingIgnoreCase(String nombre);
	
	// consulta para buscar por nombre usuario completo o solo algunos caracteres
	public List<UsuarioCliente> findByUsuarioContainingIgnoreCase(String usuario);
	
	// busqueda por usuario
	public UsuarioCliente findByUsuario(String usuario);
	
}
