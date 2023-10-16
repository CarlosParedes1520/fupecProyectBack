package com.example.prueba.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.prueba.model.Transacciones;

@Repository
public interface ITransaccionesRepo extends JpaRepository<Transacciones, Long> {
	//consulta para obtener transacciones por el numero de cuenta
	  @Query("from Transacciones tr where tr.cuenta.cuen_num =?1")
	  public List<Transacciones> getTransacciones(String num_cuenta);
	  
	}


