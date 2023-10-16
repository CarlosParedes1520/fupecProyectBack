package com.bolsadeidead.springboot.backend.fupec.repositories;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bolsadeidead.springboot.backend.fupec.models.Cliente;
import com.bolsadeidead.springboot.backend.fupec.models.SeguimientoCliente;

/*
 * Repositorio jpa Dao de Seguimiento
 * configuracion apra las consultas a la base de datos
 * 
 * */

@Repository
public interface ISeguimientoClienteRepo extends JpaRepository<SeguimientoCliente, Long> {
	
	// seguimiento por id
	public SeguimientoCliente findByIdAndEliminado(Long id, Boolean eliminado);
	
	// buscar seguimiento por cliente
	public SeguimientoCliente findByEliminadoAndIdAndCliente(Boolean eliminado, Long id, Cliente cliente); 
	
	// Para paginar seguimiento
	public Page<SeguimientoCliente> findByEliminado(Pageable pageable, Boolean eliminado);
	
	// Para buscar por fecha y dar un paginable 
	@Query(value= "SELECT sc FROM SeguimientoCliente sc "
				+ "WHERE sc.fecha Between :desde AND :hasta AND sc.cliente.eliminado = true "
				+ "ORDER BY sc.cliente.usuario.nombre ASC ")
	public Page<SeguimientoCliente> findByFechas(Pageable pageable, @Param("desde") Date desde, @Param("hasta") Date hasta);
	
	// para reporte por rango fechas y nivel satisfaccion
	@Query(value= "SELECT sc FROM SeguimientoCliente sc "
				+ "WHERE sc.fecha Between :desde AND :hasta AND sc.cliente.eliminado = true  AND sc.nivelSatisfaccion = :nivel "
				+ "ORDER BY sc.cliente.usuario.nombre ASC ")
	public List<SeguimientoCliente> findByFechasAndNivelSatisfaccionReporte (	@Param("desde") Date desde, 
																				@Param("hasta") Date hasta, 
																				@Param("nivel") Integer nivel);
}
