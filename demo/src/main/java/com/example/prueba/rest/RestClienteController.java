package com.example.prueba.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.prueba.model.Cliente;
import com.example.prueba.service.ClienteService;

@RestController
@CrossOrigin("*")
@RequestMapping("/cliente")
public class RestClienteController {
 
 @Autowired
 private ClienteService cliServicio;
 
 
 //agregar cliente
 @PostMapping(path = "/add", consumes = "application/json", produces = "application/json")
 public Cliente registrarCliente(@Validated @RequestBody Cliente cli) {
  return cliServicio.crearCliente(cli);
 }
 
//////*Buscar Cliente por Cedula*//////
 @GetMapping("/find/{id}")
   public Cliente buscarPorCedula(@PathVariable long id) {
     return cliServicio.findById(id);
   }
 
 //////* Listar Personas *//////
 @GetMapping(path = "/list", produces = "application/json")
 public List<Cliente> listarCliente() {
  return cliServicio.listarCliente();
 }
 
 @GetMapping("/findByCedula/{cedula}")
 public Cliente buscarPorCedula(@PathVariable String cedula) {
  return cliServicio.buscarClienteXCedula(cedula);
 }
 

 
@PutMapping("/mod/{id}")
 public void updateCli(@RequestBody Cliente request,@PathVariable long id) {
   cliServicio.modificarCliente(request, id);
 }


	@DeleteMapping("/delete/{id}")
	public void borrarXid(@PathVariable long id) {

		cliServicio.eliminarClienteXid(id);
		
	}
}