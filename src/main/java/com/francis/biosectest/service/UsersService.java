package com.francis.biosectest.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.francis.biosectest.dto.ActivateUserRequest;
import com.francis.biosectest.dto.PasswordResetDto;
import com.francis.biosectest.dto.ResendUserActivationCodeDto;
import com.francis.biosectest.dto.ResendUserPasswordDto;
import com.francis.biosectest.dto.ServerResponse;
import com.francis.biosectest.dto.SignInRequest;
import com.francis.biosectest.dto.SignUpRequest;
import com.francis.biosectest.dto.UpdateUserRequestDto;
import com.francis.biosectest.model.Users;




@Service
public interface UsersService {
	
	public Collection<Users> findAll();
	
	public Users findById(long usersId);
	
	public Users findByEmail(String emailAddress);
	
	public Users findByPhone(String phoneNumber);
	
	public Users findByEmailOrPhone(String emailAddress, String phoneNumber);

	public ServerResponse create(SignUpRequest request);
	
	public ServerResponse userActivation(ActivateUserRequest request);
	
	public ServerResponse reSendUserActivation(ResendUserActivationCodeDto request);
	
	public ServerResponse reSendUserPassword(ResendUserPasswordDto request);
	
	public ServerResponse passwordReset(PasswordResetDto request);
	
	public ServerResponse updateUser(UpdateUserRequestDto request);
	
	public ServerResponse login(SignInRequest request);
	
	public ServerResponse viewAll();
	
	public ServerResponse delete(long usersId);
	

}
