package com.bolsadeidead.springboot.backend.fupec.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * Modelo de la tabla seguimientos,  orm
 * Configuracion de los datos de la tabla y relaciones
 * 
 * */

@Entity
@Table(name = "seguimientos_clientes")

public class SeguimientoCliente implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	@Size(min = 2, message = "debe tener mínimo 2 carecteres")
	private String observacion;

	@NotNull(message = "no puede ser vacía")
	private Integer nivelSatisfaccion;

	@Size(min = 2, message = "debe tener minimo 2 carecteres")
	private String razonInconveniente;

	@Size(min = 2, message = "debe tener minimo 2 carecteres")
	private String sugerenciaCliente;

	@Temporal(TemporalType.DATE)
	private Date fechaGrabado;

	@NotNull(message = "no puede ser nulo")
	private Boolean eliminado;

	// relacion *:1 entre seguimiento y usuario auth
	@NotNull(message = "no puede ser vacia")
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private UserAuth user;
	
	// relacion 1:* bidireccional
	@JsonIgnoreProperties(value = { "seguimientos", "hibernateLazyInitializer", "handler" }, allowSetters = true)
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

	// Para guardar la fecha de grabado
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Integer getNivelSatisfaccion() {
		return nivelSatisfaccion;
	}

	public void setNivelSatisfaccion(Integer nivelSatisfaccion) {
		this.nivelSatisfaccion = nivelSatisfaccion;
	}

	public String getRazonInconveniente() {
		return razonInconveniente;
	}

	public void setRazonInconveniente(String razonInconveniente) {
		this.razonInconveniente = razonInconveniente;
	}

	public String getSugerenciaCliente() {
		return sugerenciaCliente;
	}

	public void setSugerenciaCliente(String sugerenciaCliente) {
		this.sugerenciaCliente = sugerenciaCliente;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public UserAuth getUser() {
		return user;
	}

	public void setUser(UserAuth user) {
		this.user = user;
	}

	private static final long serialVersionUID = 1L;

}
