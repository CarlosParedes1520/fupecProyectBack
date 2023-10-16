package com.bolsadeidead.springboot.backend.fupec.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/*
 * Modelo de la tabla usuarios,  orm
 * Configuracion de los datos de la tabla y relaciones
 * 
 * */

@Entity
@Table(name = "tabla_usuarios")
public class UsuarioCliente implements Serializable {
	// mapeamos datos de la tabla tabla_usuarios de la bd
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "no puede ser vacía")
	@Email
	private String usuario;
	
	@Size(min = 2, max = 60, message = "debe de tener entre 2 y 60 carecteres")
	private String clave;
	
	@Size(min = 2, max = 60, message = "debe de tener entre 2 y 60 carecteres")
	private String nombre;
	
	@NotEmpty(message = "no puede ser vacía")
	private String telefono;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	private static final long serialVersionUID = 1L;

}
