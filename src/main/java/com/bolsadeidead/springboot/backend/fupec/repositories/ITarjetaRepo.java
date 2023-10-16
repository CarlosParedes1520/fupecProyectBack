package com.bolsadeidead.springboot.backend.fupec.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bolsadeidead.springboot.backend.fupec.models.Tarjeta;

/*
 * Repositorio jpa Dao de Tarjeta
 * configuracion apra las consultas a la base de datos
 * 
 * */

@Repository
public interface ITarjetaRepo extends JpaRepository<Tarjeta, Long> {
	
	// buscar por codigo si la tarjeta esta activa  entreg un paginable
	public Page<Tarjeta> findByActivoAndCodigoContainingIgnoreCase(Pageable pageable, Boolean activo, String termino);

	// Para paginar las tarjetas
	public Page<Tarjeta> findByActivo(Pageable pageable, Boolean activo);

	// lista de tarjetas si estan activas por codigo
	public List<Tarjeta> findByActivoAndCodigoContainingIgnoreCase(Boolean eliminado, String termino);

	// para buscar una tarjeta y actualizar
	public Tarjeta findByIdAndActivo(Long id, Boolean activo);

	// para buscar por codigo y estado activo de tarjeta
	public Tarjeta findByCodigoAndActivo(String codigo, Boolean activo);
	
}
