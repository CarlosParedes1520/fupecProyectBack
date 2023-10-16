package com.example.prueba.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prueba.model.Transacciones;
import com.example.prueba.repository.ITransaccionesRepo;

@Service
public class TransaccionesService {

  @Autowired
  private ITransaccionesRepo repo;

  public Transacciones crearTransaccion(Transacciones tran) {
    return repo.save(tran);
  }
  
  public Transacciones buscarTransaccion(long id) {
    return repo.findById(id).orElse(null);
  }

  public List<Transacciones> listarTransacciones() {
    return repo.findAll();
  }
  
  public List<Transacciones> getTransacciones(String num_cuenta){
    return repo.getTransacciones(num_cuenta);
  }

}