package com.bolsadeidead.springboot.backend.fupec.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bolsadeidead.springboot.backend.fupec.models.Interes;

/*
 * Repositorio jpa Dao de Interes
 * configuracion apra las consultas a la base de datos
 * 
 * */

@Repository
public interface IInteresesRepo extends JpaRepository<Interes, Long> {
	
	// interes por id
	public Interes findByIdAndEliminado(Long id, Boolean eliminado);

	// interes por nombre
	public Interes findByNombreAndEliminado(String nombre, Boolean eliminado);

	// Para paginar interes activos
	public Page<Interes> findByEliminado(Pageable pageable, Boolean eliminado);
	
	// lista de intereses por nombre al filtar
	public List<Interes> findByEliminadoAndNombreContainingIgnoreCase(Boolean eliminado, String termino);
	
	// lista de intereses por nombre al filtar
	public List<Interes> findByEliminado(Boolean eliminado);

	// buscar por nombre si el interes si esta activo y da un paginable
	public Page<Interes> findByEliminadoAndNombreContainingIgnoreCase(Pageable pageable, Boolean eliminado,
			String termino);

}
