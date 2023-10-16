package com.bolsadeidead.springboot.backend.fupec.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeidead.springboot.backend.fupec.models.Interes;
import com.bolsadeidead.springboot.backend.fupec.repositories.IInteresesRepo;

/*
 * Servicios para la comuncicacion de repositorio y controlador de Intereses
 * Leer, escribir y pasar datos
 * 
 * */

@Service
public class InteresService {
	@Autowired
	private IInteresesRepo interesRepo;

	// crear un elemento nuevo
	@Transactional
	public Interes crearInteres(Interes interes) { 
		return interesRepo.save(interes);
	}

	// buscar por id
	@Transactional(readOnly = true)
	public Interes buscarIdInteres(Long id) { 
		return interesRepo.findByIdAndEliminado(id, true);
	}

	// buscar por nombre
	@Transactional(readOnly = true)
	public Interes buscarNombreInteres(String nombre) {
		return interesRepo.findByNombreAndEliminado(nombre, true);
	}
	
	// para filtrar los intereses
	@Transactional(readOnly = true)
	public List<Interes> listaInteresFiltradoPorNombre(){
		return interesRepo.findByEliminado(true);
	}
	
	// paginable de intereses si estan activos
	@Transactional(readOnly = true)
	public Page<Interes> obtenerInteresPage(Pageable pageable) {
		return interesRepo.findByEliminado(pageable, true);
	}
	
	// paginables de intereses  por nombre 
	@Transactional(readOnly = true)
	public Page<Interes> filtrarInteresNombre(Pageable pageable, String termino) {
		return interesRepo.findByEliminadoAndNombreContainingIgnoreCase(pageable, true, termino);
	}

}
