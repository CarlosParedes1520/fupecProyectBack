package com.example.prueba.model;

import java.util.Date;

import org.antlr.v4.runtime.misc.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "Transacciones")
public class Transacciones {

  @Id
  @Column(name="tran_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "PersonasTable")
  private Long id_transaccion;
  
  
  @Column(name="tran_monto_trasaccion")
  @NotNull
  private double precio ;
  
  @Column(name="tran_fecha")
  @Temporal(TemporalType.DATE)
  private  Date  fecha;
  
  @Column(name="tran_tipo")
  private  String tipo; 

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cuenta_fk")
  @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
  private Cuentas cuenta;

  public Long getId_transaccion() {
    return id_transaccion;
  }

  public void setId_transaccion(Long id_transaccion) {
    this.id_transaccion = id_transaccion;
  }

  public double getPrecio() {
    return precio;
  }

  public void setPrecio(double precio) {
    this.precio = precio;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public Cuentas getCuenta() {
    return cuenta;
  }

  public void setCuenta(Cuentas cuenta) {
    this.cuenta = cuenta;
  }
  
}