package com.francis.biosectest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.francis.biosectest.constants.ServerResponseStatus;
import com.francis.biosectest.dto.ServerResponse;
import com.francis.biosectest.service.PrivilegeService;

//import com.bizzdesk.inventory.constant.ServerResponseStatus;
//import com.bizzdesk.inventory.dto.ServerResponse;
//import com.bizzdesk.inventory.service.PrivilegeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(tags = "Privilege Management", description = "Endpoint")
@RequestMapping(value = "/privilege", produces = "application/json")
@Controller
public class PrivilegeController {

	@Autowired
	PrivilegeService privilegeService;
	
	
	
	@ApiOperation(value = "Get all user privileges", response = ServerResponse.class)
    @RequestMapping(value = "/get-all-privileges", method = RequestMethod.GET)
    @ResponseBody
//  @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ServerResponse getAllPrivileges(@RequestHeader("Authorization") String authorization){
		
		ServerResponse response = new ServerResponse();
		
		try {
			
			response = privilegeService.getPrivileges();
		
		} catch (Exception e) {
			response.setData("An error occured while fetching privileges" + e.getMessage());
			response.setMessage("An error occured while fetching users privileges");
	        response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);
		}
		return response;
		
	}
	
	
	
	@ApiOperation(value = "Get privilege by role name", response = ServerResponse.class)
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	@ResponseBody
//	@PreAuthorize("hasAuthority('UPDATE') or hasAuthority('CREATE')")
	public ServerResponse getPrivilegeByName(@RequestHeader("Authorization") String authorization, @PathVariable("name") String name){
		
		ServerResponse response = new ServerResponse();
		
		
		try {
			response = privilegeService.getPrivilegesByRole(name);
			
		} catch (Exception e) {
			response.setSuccess(false);
			response.setData("An error occured => " + e.getMessage());
			response.setMessage("An error occured while fetching privileges");
            response.setStatus(ServerResponseStatus.FAILED);
		}
		return response;
		
	}
	
	
}
