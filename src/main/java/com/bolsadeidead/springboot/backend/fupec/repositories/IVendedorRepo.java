package com.bolsadeidead.springboot.backend.fupec.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bolsadeidead.springboot.backend.fupec.models.Vendedor;

/*
 * Repositorio jpa Dao de vendedor
 * configuracion apra las consultas a la base de datos
 * 
 * */

@Repository
public interface IVendedorRepo extends JpaRepository<Vendedor, Long> {

	// para buscar un vendedor y actualizar
	public Vendedor findByIdAndEliminado(Long id, Boolean eliminado);

	// para buscar un vendedor por email
	public Vendedor findByEmailAndEliminado(String email, Boolean eliminado);

	// Para paginar vendedor
	public Page<Vendedor> findByEliminadoOrderByNombresAsc(Pageable pageable, Boolean eliminado);

	// buscar por nombres si el vendedor esta activo entrega un paginable
	public Page<Vendedor> findByEliminadoAndNombresContainingIgnoreCaseOrderByNombresAsc(Pageable pageable,
			Boolean eliminado, String termino);

	// buscar por apellidos si el vendedor esta activo entrega un paginable
	public Page<Vendedor> findByEliminadoAndApellidosContainingIgnoreCaseOrderByNombresAsc(Pageable pageable,
			Boolean eliminado, String termino);

	// buscar por cedula si el vendedor esta activo entrega un paginable
	public Page<Vendedor> findByEliminadoAndEmailContainingIgnoreCaseOrderByNombresAsc(Pageable pageable,
			Boolean eliminado, String email);

	// lista de vendedores por email
	public List<Vendedor> findByEliminadoAndEmailContainingIgnoreCase(Boolean eliminado, String termino);

	// lista de de clientes que contengan un seguimiento usando query
	@Query(value = "SELECT v FROM Vendedor v, Cliente c " 
				 + "WHERE c.vendedor.id = v.id " 
				 + "GROUP BY (v.id)"
				 + "ORDER BY v.nombres ASC ")
	public List<Vendedor> findByVendedorCliente();
	
	//lista vendedores con clientes filtrado por nombre vendedor 
	@Query(value = "SELECT v FROM Vendedor v, Cliente c " 
				 + "WHERE c.vendedor.id = v.id and v.nombres like %?1%"
				 + "GROUP BY (v.id)" + "ORDER BY v.nombres ASC ")
	public List<Vendedor> findByVendedorClienteNombre(String termino);
}
