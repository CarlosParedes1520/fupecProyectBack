package com.example.prueba.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prueba.model.Cliente;
import com.example.prueba.repository.IClienteRepo;

@Service
public class ClienteService {
  @Autowired
  private IClienteRepo repo;

  public Cliente crearCliente(Cliente cli) {
    return repo.save(cli);
  }
  
  public Cliente findById(long id) {
    return repo.findById(id).orElse(null);
  }
  
  public Cliente buscarClienteXCedula(String cedula) {
    return repo.findByCedula(cedula);
  }


  public void modificarCliente(Cliente request, Long id) {
	  Cliente cli = repo.findById(id).get();
	  System.out.println(request.getNombre());
	  cli.setNombre(request.getNombre());
	  cli.setApellido(request.getApellido());
	  cli.setCedula(request.getCedula());
	    repo.save(cli);
 }
  
  public void eliminarClienteXid(long id) {  
		repo.deleteById(id);
 }

  
  public List<Cliente> listarCliente() {
    return repo.findAll();
  }


}