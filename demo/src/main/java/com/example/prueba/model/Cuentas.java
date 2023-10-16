package com.example.prueba.model;

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

@Entity
@Table(name = "Cuentas")
public class Cuentas {
  
  @Id
  @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "CuentaTable")
  private Long id_cuenta;
  
  @Column(name="cuen_num")
  @NotNull
  private String cuen_num;
  
  @Column(name="cuen_moneda")
  @NotNull
  private String moneda ;

  @Column(name="cuen_saldo")
  @NotNull
  private Double saldo ;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cliente_fk")
  @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
  private Cliente cliente;

  public Long getId_cuenta() {
    return id_cuenta;
  }

  public void setId_cuenta(Long id_cuenta) {
    this.id_cuenta = id_cuenta;
  }

  public String getCuen_num() {
    return cuen_num;
  }

  public void setCuen_num(String cuen_num) {
    this.cuen_num = cuen_num;
  }

  public String getMoneda() {
    return moneda;
  }

  public void setMoneda(String moneda) {
    this.moneda = moneda;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

}
