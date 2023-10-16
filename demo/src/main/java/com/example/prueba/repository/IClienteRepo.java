package com.example.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.prueba.model.Cliente;

@Repository
public interface IClienteRepo extends JpaRepository<Cliente, Long> {

	@Query("SELECT c FROM Cliente c WHERE c.cedula = ?1")
	public Cliente findByCedula(String cedula);

}