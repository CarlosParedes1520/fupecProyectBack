package com.example.prueba.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.prueba.model.Transacciones;
import com.example.prueba.service.TransaccionesService;

@RestController
@CrossOrigin
@RequestMapping("/transacciones")
public class RestTransaccionesController {

  @Autowired
  private TransaccionesService TranServicio;
  
  //agregar transaccion
  @PostMapping(path = "/add", consumes = "application/json", produces = "application/json")
  public Transacciones registrarCliente(@Validated @RequestBody Transacciones tran) {
    return TranServicio.crearTransaccion(tran);
  }
  /*
  //editar transa
  @PostMapping(path = "/edit/{ced}")
  public ResponseEntity<Cliente> editarCliente(@PathVariable String ced, @RequestBody Cliente cli) {
    Cliente actualiza = cliServicio.buscarClienteXCedula(ced);
     if (actualiza == null) {      
            System.out.print("Cedula " + ced + " no existe");
            ResponseEntity.badRequest().build();
        }
    actualiza.setId_cliente(cli.getId_cliente());
    actualiza.setCedula(cli.getCedula());
    actualiza.setNombre(cli.getNombre());
    actualiza.setApellido(cli.getApellido());
  
    return ResponseEntity.ok(cliServicio.crearCliente(actualiza));
  }
  */
  ///// Eliminar transacion //////
  /*
  @DeleteMapping("/delete/{ced}")
    public ResponseEntity<?> delete(@PathVariable String ced) {
    Cliente cliente = cliServicio.buscarClienteXCedula(ced);
    if (cliente == null) {
      ResponseEntity.badRequest().build();
        }  
    cliServicio.eliminarClienteXCedula(ced);
     
     return ResponseEntity.ok().build();
    }
  */

  
   ////*Buscar por id*//////
  @GetMapping("/find/{id}")
    public Transacciones buscarPorId(@PathVariable int id) {
      return TranServicio.buscarTransaccion(id);
    }
  
  //////* Listar transacciones *//////
  @GetMapping(path = "/list", produces = "application/json")
  public List<Transacciones> listarTransacciones() {
    return TranServicio.listarTransacciones();
  }
  
  @GetMapping(path = "/get/{num_cuenta}", produces = "application/json")
  public List<Transacciones> getTransacciones(@PathVariable String num_cuenta) {
    return TranServicio.getTransacciones(num_cuenta);
  }
  
}