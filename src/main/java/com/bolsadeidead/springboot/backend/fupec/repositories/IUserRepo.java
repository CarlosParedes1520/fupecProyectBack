package com.bolsadeidead.springboot.backend.fupec.repositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bolsadeidead.springboot.backend.fupec.models.UserAuth;

/*
 * Repositorio jpa Dao de user
 * configuracion apra las consultas a la base de datos
 * 
 * */

@Repository
public interface IUserRepo extends CrudRepository<UserAuth, Long>{
	
	// buscar usuarios por nombre usuario y esten activos
	public UserAuth findByUsernameAndStatus(String username, Integer status);
	
	// buscar usuarios por id y esten activos
	public UserAuth findByIdAndStatus(Long id, Integer status);
}
