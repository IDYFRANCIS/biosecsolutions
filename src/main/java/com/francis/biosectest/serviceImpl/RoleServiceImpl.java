package com.francis.biosectest.serviceImpl;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.francis.biosectest.constants.ServerResponseStatus;
import com.francis.biosectest.dto.ServerResponse;
import com.francis.biosectest.model.Role;
import com.francis.biosectest.repository.RoleRepository;
import com.francis.biosectest.service.RoleService;



@Transactional
@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	@Override
	public Role findById(long id) {
		
		try {
			return roleRepository.findById(id);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Role findByName(String name) {
		
		try {
			return roleRepository.findByName(name);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return null;
	}

	@Override
	public Collection<Role> getRoles() {
		
		try {
			return (Collection<Role>) roleRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return null;
	}

	@Override
	public ServerResponse findAllRole() {
		
		ServerResponse response  = new ServerResponse();
		
		try {
			Collection<Role> role = getRoles();
			
			response.setData(role);
			response.setStatus(ServerResponseStatus.OK);
			
			
		} catch (Exception e) {
			
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.FAILED);
			response.setData("Failed to fetch list of roles" + e.getMessage());
			response.setMessage("Failed to fetch list of roles");
			e.printStackTrace();
			
		}
		return response;
	}

	
	
	

}
