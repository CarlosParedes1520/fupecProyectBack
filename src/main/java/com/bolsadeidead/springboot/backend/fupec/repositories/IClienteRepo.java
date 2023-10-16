package com.bolsadeidead.springboot.backend.fupec.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bolsadeidead.springboot.backend.fupec.models.Cliente;
import com.bolsadeidead.springboot.backend.fupec.models.Interes;
import com.bolsadeidead.springboot.backend.fupec.models.Tarjeta;
import com.bolsadeidead.springboot.backend.fupec.models.Vendedor;
import com.bolsadeidead.springboot.backend.fupec.models.UsuarioCliente;

/*
 * Repositorio jpa Dao de Cliente
 * configuracion apra las consultas a la base de datos
 * 
 * */

@Repository
public interface IClienteRepo extends JpaRepository<Cliente, Long> {
	// cliente por id
	public Cliente findByIdAndEliminado(Long id, Boolean eliminado);
	
	// cliente por cedula
	public Cliente findByCedulaAndEliminado(String cedula, Boolean eliminado);
	
	// cliente por UsuarioCliente
	public Cliente findByUsuarioAndEliminado(UsuarioCliente usuario, Boolean eliminado);
	
	// cliente por Tarjeta 
	public Cliente findByTarjetaAndEliminado(Tarjeta tarjeta, Boolean eliminado);
	
	// clientes por vendedor 
	public List<Cliente> findByVendedorAndEliminado(Vendedor vendedor, Boolean eliminado);
	
	// lista de clientes por cedula al filtar
	public List<Cliente> findByEliminadoAndCedulaContainingIgnoreCase(Boolean eliminado, String Cedula);
	
	// lista de clientes por intereses 
	public List<Cliente> findByEliminadoAndIntereses(Boolean eliminado, Interes interes );
	
	// busqueda para filtrar por nombre
	@Query(value="select c from Cliente c where c.eliminado=true and c.usuario.nombre like %?1% ")
	public List<Cliente> findByNombreUsuarioLista(String termino);
	
	// Para paginar cliente
	@Query(value= "select c from Cliente c where c.eliminado=true "
				+ "ORDER BY c.usuario.nombre ASC")
	public Page<Cliente> findByEliminado(Pageable pageable);		
	
	// paginacion por cedula si el cliente esta activo
	@Query(value= "select c from Cliente c where c.eliminado=true and c.cedula like %?1% "
			+ "ORDER BY c.usuario.nombre ASC")
	public Page<Cliente> findByEliminadoAndCedula(Pageable pageable, String termino);
	
	// paginacion por nombre si el cliente esta activo 
	@Query(value= "select c from Cliente c where c.eliminado=true and c.usuario.nombre like %?1% "
				+ "ORDER BY c.usuario.nombre ASC")
	public Page<Cliente> findByNombreUsuario(Pageable pageable, String termino);
	
	// paginacion por email o usuario si el cliente esta activo
	@Query(value= "select c from Cliente c where c.eliminado=true and c.usuario.usuario like %?1% "
				+ "ORDER BY c.usuario.nombre ASC")
	public Page<Cliente> findByUsuarioEmail(Pageable pageable, String termino);
	
	// lista de de clientes que contengan un seguimiento usando query
	@Query(value= "SELECT c FROM Cliente c, SeguimientoCliente sc "
				+ "WHERE sc.cliente.id = c.id AND c.eliminado = 1 "
				+ "GROUP BY (c.id)" 
				+ "ORDER BY c.usuario.nombre ASC ")
	public Page<Cliente> findBySegumientosCliente(Pageable pageable);
	
	// busqueda para filtrar por nombre y que tenga seguimientos
	@Query(value= "SELECT c FROM Cliente c, SeguimientoCliente sc "
				+ "WHERE sc.cliente.id = c.id AND c.eliminado=true AND c.usuario.nombre like %?1% "
				+ "GROUP BY (c.id)"
				+ "ORDER BY c.usuario.nombre ASC ")
	public Page<Cliente> findBySeguimientosClientesPorNombre(Pageable pageable, String termino);
	
	// busqueda para filtrar por cedula y tenga seguiminetos
	@Query(value= "SELECT c FROM Cliente c, SeguimientoCliente sc "
				+ "WHERE sc.cliente.id = c.id AND c.eliminado=true AND c.cedula like %?1% "
				+ "GROUP BY (c.id)"
				+ "ORDER BY c.usuario.nombre ASC ")
	public Page<Cliente> findBySeguimientosClientesPorCedula(Pageable pageable, String termino);
}
