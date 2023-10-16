package com.bolsadeidead.springboot.backend.fupec.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * Modelo de la tabla user_groups,  orm
 * Configuracion de los datos de la tabla y relaciones
 * 
 * */

@Entity
@Table(name = "user_groups")
public class UserGroup implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String groupName;
	
	@Column(unique = true)
	private Integer groupLevel;

	private Integer groupStatus;

	// getters and setters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getGroupLevel() {
		return groupLevel;
	}

	public void setGroupLevel(Integer groupLevel) {
		this.groupLevel = groupLevel;
	}

	public Integer getGroupStatus() {
		return groupStatus;
	}

	public void setGroupStatus(Integer groupStatus) {
		this.groupStatus = groupStatus;
	}

	private static final long serialVersionUID = 1L;

}
