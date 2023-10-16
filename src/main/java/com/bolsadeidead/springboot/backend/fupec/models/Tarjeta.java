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
import javax.validation.constraints.Size;

/*
 * Modelo de la tabla tarjetas,  orm
 * Configuracion de los datos de la tabla y relaciones
 * 
 * */

@Entity
@Table(name = "tarjetas")
public class Tarjeta implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 2, max = 60, message = "debe tener entre 2 y 60 caracteres")
	private	String codigo;	
	
	@Temporal(TemporalType.DATE)
	private Date fechaGrabado;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCaducar;
	
	private Boolean activo;
	
	@PrePersist
	public void peristenciaFechaGrabado() { // para guardar fecha desde el servidor
		this.fechaGrabado = new Date();
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Date getFechaGrabado() {
		return fechaGrabado;
	}

	public void setFechaGrabado(Date fechaGrabado) {
		this.fechaGrabado = fechaGrabado;
	}

	public Date getFechaCaducar() {
		return fechaCaducar;
	}

	public void setFechaCaducar(Date fechaCaducar) {
		this.fechaCaducar = fechaCaducar;
	}

	private static final long serialVersionUID = 1L;
}
