package com.francis.biosectest.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.francis.biosectest.model.Privilege;
import com.francis.biosectest.model.Role;



@Repository
public interface PrivilegeRepository extends CrudRepository<Privilege, Long>{
	
	public Privilege findById(long id);

	public Privilege findByName(String name);
	
	public  Collection<Privilege> findAllByRoles(Role role);
	
	public Collection<Privilege> findByRoles_name(String name);
	

}
