package com.example.prueba.model;


import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
*/

@Entity
@Table(name = "Cliente")
public class Cliente {

  @Id
  @Column(name="cli_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "PersonasTable")
  private Long id_cliente;
  
  @Column(name="cli_cedula", unique=true)
  private String cedula;
  
  @Column(name="cli_nombre", length=50)
  @NotNull
  private String nombre;
  
  @Column(name="cli_apellido", length=50)
 
  private String apellido;
  
  //get y set
  public Long getId_cliente() {
    return id_cliente;
  }


  public void setId_cliente(Long id_cliente) {
    this.id_cliente = id_cliente;
  }


  public String getCedula() {
    return cedula;
  }


  public void setCedula(String cedula) {
    this.cedula = cedula;
  }


  public String getNombre() {
    return nombre;
  }


  public void setNombre(String nombre) {
    this.nombre = nombre;
  }


  public String getApellido() {
    return apellido;
  }


  public void setApellido(String apellido) {
    this.apellido = apellido;
  }
}