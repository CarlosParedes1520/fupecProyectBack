package com.bolsadeidead.springboot.backend.fupec.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * Modelo de la tabla clientes,  orm
 * Configuracion de los datos de la tabla y relaciones
 * 
 * */

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(min = 10, max = 10, message = "debe tener 10 dígitos")
	private String cedula;

	@NotEmpty(message = "no puede ser vacía")
	private String genero;

	@NotEmpty(message = "no puede ser vacía")
	private String ocupacion;

	@NotEmpty(message = "no puede ser vacía")
	private String tipoSangre;

	@NotNull(message = "no puede ser vacía")
	private Integer puntos;

	@Temporal(TemporalType.DATE)
	private Date fechaGrabado;

	@NotNull(message = "no puede ser nulo")
	private Boolean eliminado;

	// relaciones BD

	// relacion 1:1 entre Cliente y usuarioCliente
	@NotNull(message = "no puede ser vacia1")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" }) // no falle el json al front
	@OneToOne(fetch = FetchType.LAZY) // carga lenta
	@JoinColumn(referencedColumnName = "id")
	private UsuarioCliente usuario;

	// relacion 1:1 entre cliente y tarjeta
	@NotNull(message = "no puede ser vacia")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id")
	private Tarjeta tarjeta;

	// relacion *:1 entre cliente y vendedor
	@NotNull(message = "no puede ser vacia")
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Vendedor vendedor;

	// relacion *:1 entre cliente y usuario Auth
	@NotNull(message = "no puede ser vacia")
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private UserAuth user;
	
	// relacion muchos a muchos con tabala intereses
	@ManyToMany
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JoinTable(name = "clientes_intereses", joinColumns = @JoinColumn(name = "cliente_id"), inverseJoinColumns = @JoinColumn(name = "interes_id"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "cliente_id", "interes_id" }) })
	private List<Interes> intereses = new ArrayList<Interes>();

	// realcion uno a muchos tabla seguimiento_cliente
	@JsonIgnoreProperties(value = { "cliente", "hibernateLazyInitializer", "handler" }, allowSetters = true)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<SeguimientoCliente> seguimientos;

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

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	public String getTipoSangre() {
		return tipoSangre;
	}

	public void setTipoSangre(String tipoSangre) {
		this.tipoSangre = tipoSangre;
	}

	public Integer getPuntos() {
		return puntos;
	}

	public void setPuntos(Integer puntos) {
		this.puntos = puntos;
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

	private static final long serialVersionUID = 1L;

	// get and setters relaciones 1*

	public UsuarioCliente getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioCliente usuario) {
		this.usuario = usuario;
	}

	public Tarjeta getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(Tarjeta tarjeta) {
		this.tarjeta = tarjeta;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public List<Interes> getIntereses() {
		return intereses;
	}

	public void setIntereses(List<Interes> intereses) {
		this.intereses = intereses;
	}

	public List<SeguimientoCliente> getSeguimientos() {+
		return seguimientos;
	}

	public void setSeguimientos(List<SeguimientoCliente> seguimientos) {
		this.seguimientos = seguimientos;
	}

	public UserAuth getUser() {
		return user;
	}

	public void setUser(UserAuth user) {
		this.user = user;
	}
}
