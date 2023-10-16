package com.bolsadeidead.springboot.backend.fupec.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * Modelo de la tabla users,  orm
 * Configuracion de los datos de la tabla y relaciones
 * 
 * */

@Entity
@Table(name = "users")
public class UserAuth implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String name;

	@Column(unique = true)
	private String username;

	@Column
	private String password;

	@Column
	private String image;

	@Column
	private Integer status;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;
	
	// Relacion *:1 UserAuth y UserGroup
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_level")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private UserGroup userLevel;
	
	// Getters y Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	public UserGroup getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(UserGroup userLevel) {
		this.userLevel = userLevel;
	}

	private static final long serialVersionUID = 1L;
}
