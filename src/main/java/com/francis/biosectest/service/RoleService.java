package com.francis.biosectest.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.francis.biosectest.dto.ServerResponse;
import com.francis.biosectest.model.Role;



@Service
public interface RoleService {
	
	 public Role findById(long id);

	    public Role findByName(String name);

	    public Collection<Role> getRoles();
	    
	    public ServerResponse findAllRole();

}
