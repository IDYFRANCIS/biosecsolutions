package com.francis.biosectest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.francis.biosectest.model.Users;



@Repository
public interface UsersRepository extends CrudRepository<Users, Long>{
	
	public Users findByUsersId(long usersId);

	public Users findByEmailAddress(String emailAddress);
	
	public Users findByPhoneNumber(String phoneNumber);
	
	public Users findByPhoneNumberOrEmailAddress(String emailAddress, String phoneNumber);
	
	public Users findByActivationCode(String activationCode);
	
	public Users findByPasswordResetCode(String passwordResetCode);
	
	
}
