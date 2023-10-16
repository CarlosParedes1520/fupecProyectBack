package com.example.prueba.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.prueba.model.Cuentas;
import com.example.prueba.service.CuentasService;

@RestController
@CrossOrigin
@RequestMapping("/cuenta")
public class RestCuentasController {
  @Autowired
  private CuentasService cuenServicio;

  // agregar Cuenta
  @PostMapping(path = "/add", consumes = "application/json", produces = "application/json")
  public Cuentas registrarCuenta(@Validated @RequestBody Cuentas cuen) {
    return cuenServicio.crearCuenta(cuen);
  }
   //actualiza el saldo de la cuenta
  @PutMapping(path = "/edit/{id}")
  public Cuentas update(@RequestBody Cuentas cuenta, @PathVariable Long id) {
    Cuentas cuentaActual=cuenServicio.buscarXid(id);
    cuentaActual.setSaldo(cuenta.getSaldo());
    this.cuenServicio.crearCuenta(cuentaActual);
    return cuentaActual;
  }
  
  ////// *Buscar Cuenta por id*//////
  @GetMapping("/find/{id}")
  public Cuentas buscarPorId(@PathVariable int id) {
    return cuenServicio.buscarXid(id);
  }
  
  @GetMapping("/findcuen/{num_cuenta}")
  public Cuentas findCuenta(@PathVariable String num_cuenta) {
    return cuenServicio.findByCuenta(num_cuenta);
  }

  ////// * Listar Personas *//////
  @GetMapping(path = "/list", produces = "application/json")
  public List<Cuentas> listarCuentas() {
    return cuenServicio.listarCuentas();
  }
}