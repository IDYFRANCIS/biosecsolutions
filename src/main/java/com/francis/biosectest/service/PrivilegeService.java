package com.francis.biosectest.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.francis.biosectest.dto.ServerResponse;
import com.francis.biosectest.model.Privilege;


@Service
public interface PrivilegeService {
	
	 	public Privilege findById(long id);

	    public Privilege findByName(String name);

	    public ServerResponse update(Privilege privilege);

	    public ServerResponse create(Privilege privilege);

	    public ServerResponse delete(Privilege role);

	    public ServerResponse getPrivileges();
	    
	    public ServerResponse getPrivilegesByRole(String name);

	    public Collection<Privilege> findAll();
	    
	 

}
