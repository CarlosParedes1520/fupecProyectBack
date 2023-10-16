package com.bolsadeidead.springboot.backend.fupec.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeidead.springboot.backend.fupec.models.Cliente;
import com.bolsadeidead.springboot.backend.fupec.models.SeguimientoCliente;
import com.bolsadeidead.springboot.backend.fupec.repositories.ISeguimientoClienteRepo;

/*
 * Servicios para la comuncicacion de repositorio y controlador de Seguimiento de Cliente
 * Leer, escribir y pasar datos
 * 
 * */

@Service
public class SeguimientoClienteService {
	@Autowired
	private ISeguimientoClienteRepo seguimientoClienteRepo;
	
	// crear un nuevo seguimiento
	@Transactional
	public SeguimientoCliente crearSeguimientoCliente(SeguimientoCliente seguimientoCliente) { 
		return seguimientoClienteRepo.save(seguimientoCliente);
	}
	
	// buscar por id
	@Transactional(readOnly = true)
	public SeguimientoCliente buscarIdSeguimientoCliente(Long id) { 
		return seguimientoClienteRepo.findByIdAndEliminado(id, true);
	}
	
	// buscar seguimiento por cliente
	@Transactional(readOnly = true)
	public SeguimientoCliente buscarSeguimientoPorCliente(Long id, Cliente cliente) { 
		return seguimientoClienteRepo.findByEliminadoAndIdAndCliente(true, id, cliente);
	}
	
	// paginable de seguimientos
	@Transactional(readOnly = true)
	public Page<SeguimientoCliente> obtenerSeguimientoClientePage(Pageable pageable) {
		return seguimientoClienteRepo.findByEliminado(pageable, true);
	}
	
	@Transactional(readOnly = true)
	public Page<SeguimientoCliente> buscarseguimientosPorFecha(Pageable pageable, Date desde, Date hasta) {
		return seguimientoClienteRepo.findByFechas(pageable, desde, hasta);
	}
	
	// para reporte por rango fechas y nivel satisfaccion
	@Transactional(readOnly = true)
	public List<SeguimientoCliente> buscarseguimientosPorFechaNivelReporte( Date desde, Date hasta, Integer nivel) {
		return seguimientoClienteRepo.findByFechasAndNivelSatisfaccionReporte(desde, hasta, nivel);
	}

	// eliminar seguimientos
	@Transactional
	public void eliminarPorId(Long id) {
		seguimientoClienteRepo.deleteById(id);
	}	

}
