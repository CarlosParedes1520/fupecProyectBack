package com.bolsadeidead.springboot.backend.fupec.models;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * Modelo de la tabla vendedores,  orm
 * Configuracion de los datos de la tabla y relaciones
 * 
 * */

@Entity
@Table(name = "vendedores")
public class Vendedor implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Size(min = 2,max = 60, message = "debe de  debe tener entre 2 y 60 carecteres")
	private String nombres;

	@Size(min = 2,max = 60, message = "debe de tener entre 2 y 60 carecteres")
	private String apellidos;
	
	@NotEmpty(message = "no puede ser vacía")
	@Email
	private String email;
	
	@NotEmpty(message = "no puede ser vacía")
	private String telefono;
	
	@Temporal(TemporalType.DATE)
	private Date fechaGrabado;
	
	@NotNull(message = "no puede ser nulo")
	private Boolean eliminado;
	
	// relacion bidireccional 1:* entre cliente
	@JsonIgnoreProperties(value = { "vendedor", "hibernateLazyInitializer", "handler" }, allowSetters = true)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "vendedor", cascade = CascadeType.ALL)
	private List<Cliente> clientes;
	
	// Para guardar la fecha de grabado del vendedor
	@PrePersist
	public void persisteciaFechaGradabo() { // para guardar fecha desde el servidor
		this.fechaGrabado = new Date();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Date getFechaGrabado() {
		return fechaGrabado;
	}

	public void setFechaGrabado(Date fechaGrabado) {
		this.fechaGrabado = fechaGrabado;
	}

	public Boolean getEliminado() {
		return eliminado;
	}

	public void setEliminado(Boolean eliminado) {
		this.eliminado = eliminado;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	private static final long serialVersionUID = 1L;
}
