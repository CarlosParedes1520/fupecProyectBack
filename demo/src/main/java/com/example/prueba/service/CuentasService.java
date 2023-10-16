package com.example.prueba.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prueba.model.Cuentas;
import com.example.prueba.repository.ICuentaRepo;

@Service
public class CuentasService {

  @Autowired
  private ICuentaRepo repo;

  public Cuentas crearCuenta(Cuentas cuen) {
    return repo.save(cuen);
  }
  
  public Cuentas buscarXid(long id) {
    return repo.findById(id).orElse(null);
  }

  public Cuentas findByCuenta(String cedula){
    return repo.findByCuenta(cedula);
  }
  
  public List<Cuentas> listarCuentas() {
    return repo.findAll();
  }
  
}