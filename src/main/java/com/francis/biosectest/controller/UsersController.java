package com.francis.biosectest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.francis.biosectest.constants.ServerResponseStatus;
import com.francis.biosectest.dto.ActivateUserRequest;
import com.francis.biosectest.dto.PasswordResetDto;
import com.francis.biosectest.dto.ResendUserActivationCodeDto;
import com.francis.biosectest.dto.ResendUserPasswordDto;
import com.francis.biosectest.dto.ServerResponse;
import com.francis.biosectest.dto.SignInRequest;
import com.francis.biosectest.dto.SignUpRequest;
import com.francis.biosectest.dto.UpdateUserRequestDto;
import com.francis.biosectest.service.UsersService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



@Api(tags = "User Account Management", description = "Endpoint")
@RequestMapping(value = "/user", produces = "application/json")
@Controller
public class UsersController {

	@Autowired
	UsersService usersService;
	
	private HttpHeaders responseHeaders = new HttpHeaders();
	
	 

	               /*****************************************  
                      * REGISTER A USER ACCOUNT
                    *****************************************/

	@ApiOperation(value = "Register a user account", response = ServerResponse.class)
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	@ResponseBody
//	@PreAuthorize("hasAuthority('SUPER_ADMIN')")
	public ResponseEntity<?> create( @RequestBody SignUpRequest request){
			
		ServerResponse response = new ServerResponse();
		
		try {
			response = usersService.create(request);
		 
		} catch (Exception e) {
			response.setData("An error occured" + e.getMessage());
            response.setMessage("Failed to register user");
            response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);
            
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
	}
	
	
	
                         /*****************************************  
                            * VERIFY AND ACTIVATE USER ACCOUNT
                          *****************************************/
	

	@ApiOperation(value = "Verify and Activate user account", response = ServerResponse.class)
	@RequestMapping(value = "/verification", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> ActivateUser(@RequestBody ActivateUserRequest request){
		
		
		ServerResponse response = new ServerResponse();
		
		try {
			
			response = usersService.userActivation(request);
		 
		} catch (Exception e) {
			response.setData("An error occured" + e.getMessage());
            response.setMessage("Failed to verify and activate user");
            response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);
            
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
	}
	
	
	                    /*****************************************
                            * RESEND USER ACTIVATION CODE
                         *****************************************/
	
	@ApiOperation(value = "Resend user Activation code", response = ServerResponse.class)
	@RequestMapping(value = "/resnd-activation", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> ResendUserActivation(@RequestBody ResendUserActivationCodeDto request){
		
		
		ServerResponse response = new ServerResponse();
		
		try {
			
			response = usersService.reSendUserActivation(request);
		 
		} catch (Exception e) {
			response.setData("An error occured" + e.getMessage());
            response.setMessage("Failed to resend user activation code");
            response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);
            
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
	}
	
	
	                      /*****************************************
                               * PASSWORD RECOVERY CODE
                           *****************************************/

	@ApiOperation(value = "Send password recovery code to the user", response = ServerResponse.class)
	@RequestMapping(value = "password/recovery", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> PasswordRestRequest(@RequestBody ResendUserPasswordDto request){
		
		
		ServerResponse response = new ServerResponse();
		
		try {
			
			response = usersService.reSendUserPassword(request);
		 
		} catch (Exception e) {
			response.setData("An error occured" + e.getMessage());
            response.setMessage("Failed to send user password recovery code");
            response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);
            
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
	}
	
	
	                     /*****************************************
                                * RESET USER PASSWORD
                          *****************************************/
	
	@ApiOperation(value = "Change user password", response = ServerResponse.class)
	@RequestMapping(value = "password/change", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> PasswordReset(@RequestBody PasswordResetDto request){
		
		
		ServerResponse response = new ServerResponse();
		
		try {
			
			response = usersService.passwordReset(request);
		 
		} catch (Exception e) {
			response.setData("Failed to reset user password" + e.getMessage());
            response.setMessage("Failed to reset user password");
            response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);
            
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
	}
	
	
	                     /*****************************************
                                 USER LOGIN REQUEST
                          *****************************************/
	
	@ApiOperation(value = "Login user account", response = ServerResponse.class)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> Login(@RequestBody SignInRequest request){
		
		ServerResponse response = new ServerResponse();
		
		try {
			
			response = usersService.login(request);
		
		} catch (Exception e) {
			e.printStackTrace();
			response.setData("An error occured while verifying user account" + e.getMessage());
			response.setMessage("An error occured while verifying user account");
	        response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));

	}

	
	                       /*****************************************
                                          GET ALL USERS 
                            *****************************************/

	@ApiOperation(value = "Get all user accounts", response = ServerResponse.class)
    @RequestMapping(value = "/get-all-users", method = RequestMethod.GET)
    @ResponseBody
//    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> viewAll(@RequestHeader("Authorization") String authorization){
		
		ServerResponse response = new ServerResponse();
		
		try {
			
			response = usersService.viewAll();
		
		} catch (Exception e) {
			e.printStackTrace();
			response.setData("An error occured while fetching users accounts" + e.getMessage());
			response.setMessage("An error occured while fetching users accounts");
	        response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));

	}
	
	
	                       /*****************************************
                                     UPDATE USER DETAILS 
                            *****************************************/
	
	@ApiOperation(value = "Update a user account", response = ServerResponse.class)
	@RequestMapping(value = "/update-user-account", method = RequestMethod.PUT)
	@ResponseBody
//	@PreAuthorize("hasAuthority('UPDATE')")
	public ResponseEntity<?> update(@RequestHeader("Authorization") String authorization, @RequestBody UpdateUserRequestDto request){
		
		ServerResponse response = new ServerResponse();
		
		try {
			response = usersService.updateUser(request);
			
		} catch (Exception e) {
			response.setData("An error occured => " + e.getMessage());
			response.setMessage("Failed while updating user details");
			response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
	}
	
	
                             /*****************************************
                                       DELETE USER ACCOUNT
                             *****************************************/
	
	@ApiOperation(value = "Delete a user account", response = ServerResponse.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
//	@PreAuthorize("hasAuthority('DELETE')")
	public ResponseEntity<?> delete(@RequestHeader("Authorization") String authorization, @RequestParam long id){
		
		ServerResponse response = new ServerResponse();
		
		try {
			response = usersService.delete(id);
			
		} catch (Exception e) {
			response.setData("An error occured => " + e.getMessage());
			response.setMessage("Failed to delete user");
			response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
	}
	
	
	
}
