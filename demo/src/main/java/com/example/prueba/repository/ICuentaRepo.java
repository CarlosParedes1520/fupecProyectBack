package com.example.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.prueba.model.Cuentas;

@Repository
public interface ICuentaRepo extends JpaRepository<Cuentas, Long> {
  @Query("SELECT c FROM Cuentas c WHERE c.cuen_num = ?1")
  public Cuentas findByCuenta(String cedula);

}