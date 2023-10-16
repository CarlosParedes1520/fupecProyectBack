package com.bolsadeidead.springboot.backend.fupec.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
 * Modelo de la tabla intereses,  orm
 * Configuracion de los datos de la tabla y relaciones
 * 
 * */

@Entity
@Table(name = "intereses")

public class Interes implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 2, max = 60, message = "debe tener entre 2 y 60 carecteres")
	private String descripcion;
	
	@Size(min = 2, max = 60, message = "debe tener entre 2 y 60 carecteres")
	private String nombre;
	
	@NotNull(message = "no puede ser nulo")
	private Boolean eliminado;
	
	@Temporal(TemporalType.DATE)
	private Date fechaGrabado;

	// Para guardar la fecha de grabado del interes
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getEliminado() {
		return eliminado;
	}

	public void setEliminado(Boolean eliminado) {
		this.eliminado = eliminado;
	}

	public Date getFechaGrabado() {
		return fechaGrabado;
	}

	public void setFechaGrabado(Date fechaGrabado) {
		this.fechaGrabado = fechaGrabado;
	}

	private static final long serialVersionUID = 1L;

}
