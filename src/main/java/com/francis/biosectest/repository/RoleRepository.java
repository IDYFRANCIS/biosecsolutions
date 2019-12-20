package com.francis.biosectest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.francis.biosectest.model.Role;



@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{
	
	public Role findById(long id);

	public Role findByName(String name);
	

}
