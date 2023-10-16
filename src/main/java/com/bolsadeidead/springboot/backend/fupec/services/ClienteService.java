package com.bolsadeidead.springboot.backend.fupec.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeidead.springboot.backend.fupec.models.Cliente;
import com.bolsadeidead.springboot.backend.fupec.models.Interes;
import com.bolsadeidead.springboot.backend.fupec.models.Tarjeta;
import com.bolsadeidead.springboot.backend.fupec.models.UsuarioCliente;
import com.bolsadeidead.springboot.backend.fupec.models.Vendedor;
import com.bolsadeidead.springboot.backend.fupec.repositories.IClienteRepo;

/*
 * Servicios para la comuncicacion de repositorio y controlador de Cliente
 * Leer, escribir y pasar datos
 * 
 * */

@Service
public class ClienteService {
	
	@Autowired
	private IClienteRepo clienteRepo;
	
	// crear un elemento nuevo
	@Transactional
	public Cliente crearCliente(Cliente cliente) { 
		return clienteRepo.save(cliente);
	}
	
	// buscar por id del elemento
	@Transactional(readOnly = true)
	public Cliente buscarIdCliente(Long id) { 
		return clienteRepo.findByIdAndEliminado(id, true);
	}
	
	// buscar por cedula del elemento
	@Transactional(readOnly = true)
	public Cliente buscarCedulaCliente(String cedula) { 
		return clienteRepo.findByCedulaAndEliminado(cedula, true);
	}
	
	// buscar por usuariocliente 
	@Transactional(readOnly = true)
	public Cliente buscarUsuarioCliente(UsuarioCliente usuarioCliente) { 
		return clienteRepo.findByUsuarioAndEliminado(usuarioCliente, true);
	}
	
	// buscar por tarjeta
	@Transactional(readOnly = true)
	public Cliente buscarTarjetaCliente(Tarjeta tarjeta) { 
		return clienteRepo.findByTarjetaAndEliminado(tarjeta, true);
	}
	
	// buscar por vendedor 
	@Transactional(readOnly = true)
	public List<Cliente> buscarVendedorClientes(Vendedor vendedor) { 
		return clienteRepo.findByVendedorAndEliminado(vendedor, true);
	}
	
	// buscar por interes del cliente
	@Transactional(readOnly = true)
	public List<Cliente> buscarInteresClientes(Interes interes) { 
		return clienteRepo.findByEliminadoAndIntereses(true, interes);
	}
	
	// lista por nombre cliente
	@Transactional(readOnly = true)
	public List<Cliente> listarClientesPorNombre(String termino) { 
		return clienteRepo.findByNombreUsuarioLista(termino);
	}
	
	// lista por nombre cliente
	@Transactional(readOnly = true)
	public List<Cliente> listarClientesPorCedula(String termino) { 
		return clienteRepo.findByEliminadoAndCedulaContainingIgnoreCase(true, termino);
	}
	
	// regresa un paginable de clientes que esten activos
	@Transactional(readOnly = true)
	public Page<Cliente> obtenerClientesPage(Pageable pageable) {
		return clienteRepo.findByEliminado(pageable);
	}
	
	// regresa un paginable de clientes filtrando la cedula
	@Transactional(readOnly = true)
	public Page<Cliente> filtrarClienteCedula(Pageable pageable, String termino) {
		return clienteRepo.findByEliminadoAndCedula(pageable, termino);
	}
	
	// regresa un paginable de clientes filtrando el nombre
	@Transactional(readOnly = true)
	public Page<Cliente> filtrarNombreUsuarioClientes(Pageable pageable, String termino) {
		return clienteRepo.findByNombreUsuario(pageable,termino);
	}
	
	// regresa un paginable de clientes filtrando el usuario o email
	@Transactional(readOnly = true)
	public Page<Cliente> filtrarNombreEmailClientes(Pageable pageable,String termino) {
		return clienteRepo.findByUsuarioEmail(pageable,termino);
	}
	
	// regresa un paginable de clientes que contengan seguimientos
	@Transactional(readOnly = true)
	public Page<Cliente> obtenerClientesSegumientos(Pageable pageable) {
		return clienteRepo.findBySegumientosCliente(pageable);
	}
	
	// regresa un paginable de clientes que tengan seguimientos filtrando el nombre
	@Transactional(readOnly = true)
	public Page<Cliente> obtenerClientesSegumientosPorNombre(Pageable pageable, String termino) {
		return clienteRepo.findBySeguimientosClientesPorNombre(pageable, termino);
	}
	
	// regresa un paginable de clientes que tengan seguimientos filtrando la cedula 
	@Transactional(readOnly = true)
	public Page<Cliente> obtenerClientesSegumientosPorCedula(Pageable pageable, String termino) {
		return clienteRepo.findBySeguimientosClientesPorCedula(pageable, termino);
	}

}
