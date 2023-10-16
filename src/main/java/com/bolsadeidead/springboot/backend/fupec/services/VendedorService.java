package com.bolsadeidead.springboot.backend.fupec.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeidead.springboot.backend.fupec.models.Vendedor;
import com.bolsadeidead.springboot.backend.fupec.repositories.IVendedorRepo;

/*
 * Servicios para la comuncicacion de repositorio y controlador de Vendedor
 * Leer, escribir y pasar datos
 * 
 * */

@Service
public class VendedorService {

	@Autowired
	private IVendedorRepo vendedorRepo;

	// crear un nuevo vendedor
	@Transactional
	public Vendedor crearVendedor(Vendedor vendedor) {
		return vendedorRepo.save(vendedor);
	}

	// buscar por id
	@Transactional(readOnly = true)
	public Vendedor buscarIdVendedor(Long id) {
		return vendedorRepo.findByIdAndEliminado(id, true);
	}

	// para buscar por email
	@Transactional(readOnly = true)
	public Vendedor buscarEmailVendedor(String email) {
		return vendedorRepo.findByEmailAndEliminado(email, true);
	}

	// buscamos vendedores por email completo o solo una parte si no estan
	// eliminados
	@Transactional(readOnly = true)
	public List<Vendedor> listaEmailVendedor(String termino) {
		return vendedorRepo.findByEliminadoAndEmailContainingIgnoreCase(true, termino);
	}

	// paginable de vendedores activos
	@Transactional(readOnly = true)
	public Page<Vendedor> ObtenerVendedoresPage(Pageable pageable) {
		return vendedorRepo.findByEliminadoOrderByNombresAsc(pageable, true);
	}

	// paginable por filtracion del nombre
	@Transactional(readOnly = true)
	public Page<Vendedor> filtrarVendedorNombre(Pageable pageable, String termino) {
		return vendedorRepo.findByEliminadoAndNombresContainingIgnoreCaseOrderByNombresAsc(pageable, true, termino);
	}

	// paginable por filtracion del apellido
	@Transactional(readOnly = true)
	public Page<Vendedor> filtrarVendedorApellido(Pageable pageable, String termino) {
		return vendedorRepo.findByEliminadoAndApellidosContainingIgnoreCaseOrderByNombresAsc(pageable, true, termino);
	}

	// paginable por filtracion del email
	@Transactional(readOnly = true)
	public Page<Vendedor> filtrarVendedorEmail(Pageable pageable, String email) {
		return vendedorRepo.findByEliminadoAndEmailContainingIgnoreCaseOrderByNombresAsc(pageable, true, email);
	}

	// lista de vendedores con clientes para reporte
	@Transactional(readOnly = true)
	public List<Vendedor> listaVendedoresClientes() {
		return vendedorRepo.findByVendedorCliente();
	}
	// lista de vendedores con clientes por filtracion nombre para reporte
	@Transactional(readOnly = true)
	public List<Vendedor> listaVendedoresClientesNombres(String termino) {
		return vendedorRepo.findByVendedorClienteNombre(termino);
	}

}
