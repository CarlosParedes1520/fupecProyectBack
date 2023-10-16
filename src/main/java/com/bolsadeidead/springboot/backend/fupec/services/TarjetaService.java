package com.bolsadeidead.springboot.backend.fupec.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeidead.springboot.backend.fupec.models.Tarjeta;
import com.bolsadeidead.springboot.backend.fupec.repositories.ITarjetaRepo;

/*
 * Servicios para la comuncicacion de repositorio y controlador de Tarjeta
 * Leer, escribir y pasar datos
 * 
 * */

@Service
public class TarjetaService {
	@Autowired
	private ITarjetaRepo tarjetaRepo;
	
	// crear una nueva tarjeta
	@Transactional
	public Tarjeta crearTarjeta(Tarjeta tarjeta) { 
		return tarjetaRepo.save(tarjeta);
	}

	// buscar por id
	@Transactional(readOnly = true)
	public Tarjeta buscarIdTarjeta(Long id) { 
		return tarjetaRepo.findByIdAndActivo(id, true);
	}
	
	// buscar por codigo
	@Transactional(readOnly = true)
	public Tarjeta buscarcodigoTarjeta(String codigo) {
		return tarjetaRepo.findByCodigoAndActivo(codigo, true);
	}

	// buscamos tarjetas por codigo completo o solo una parte si no estan eliminados
	@Transactional(readOnly = true)
	public List<Tarjeta> listaEmailVendedor(String termino) {
		return tarjetaRepo.findByActivoAndCodigoContainingIgnoreCase(true, termino);
	}
	
	// paginable de tarjetas activas
	@Transactional(readOnly = true)
	public Page<Tarjeta> ObtenerTarjetasPage(Pageable pageable) {
		return tarjetaRepo.findByActivo(pageable, true);
	}
	
	// para filtrar por codigo, regresa un paginable
	@Transactional(readOnly = true)
	public Page<Tarjeta> filtrarTarjetasCodigo(Pageable pageable, String termino) { 
		return tarjetaRepo.findByActivoAndCodigoContainingIgnoreCase(pageable, true, termino);
	}
}
