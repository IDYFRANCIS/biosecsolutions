package com.francis.biosectest.serviceImpl;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.francis.biosectest.constants.AppConstants;
import com.francis.biosectest.constants.ServerResponseStatus;
import com.francis.biosectest.dto.ActivateUserRequest;
import com.francis.biosectest.dto.PasswordResetDto;
import com.francis.biosectest.dto.ResendUserActivationCodeDto;
import com.francis.biosectest.dto.ResendUserPasswordDto;
import com.francis.biosectest.dto.ServerResponse;
import com.francis.biosectest.dto.SignInRequest;
import com.francis.biosectest.dto.SignUpRequest;
import com.francis.biosectest.dto.UpdateUserRequestDto;
import com.francis.biosectest.mail.EmailService;
import com.francis.biosectest.mail.Mail;
import com.francis.biosectest.model.Role;
import com.francis.biosectest.model.Users;
import com.francis.biosectest.repository.RoleRepository;
import com.francis.biosectest.repository.UsersRepository;
import com.francis.biosectest.service.UsersService;
import com.francis.biosectest.utility.Utility;


@Transactional
@Service
public class UsersServiceImpl implements UsersService{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	AppConstants appConstants;
	
	@Autowired
	private EmailService emailService;
	
    
    Utility utility = new Utility();
	
    private static Logger logger = LogManager.getLogger(UsersServiceImpl.class);
	
	@Override
	public Collection<Users> findAll() {
		
		try {
			return (Collection<Users>) usersRepository.findAll();
					
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return null;
	}

	@Override
	public Users findById(long usersId) {
		
		try {
			return usersRepository.findByUsersId(usersId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Users findByEmail(String emailAddress) {
		
		try {
			return usersRepository.findByEmailAddress(emailAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Users findByPhone(String phoneNumber) {
		
		try {
			return usersRepository.findByPhoneNumber(phoneNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	@Override
	public Users findByEmailOrPhone(String emailAddress, String phoneNumber) {
		
		try {
			return usersRepository.findByPhoneNumberOrEmailAddress(phoneNumber, emailAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}


/************************************************************************************************************
 *                            USER ACCOUNT CREATION
 ***********************************************************************************************************/
	
	@Override
	public ServerResponse create(SignUpRequest request) {
	
       ServerResponse response = new ServerResponse();
		
		Users users = null;
		
		
		String firstName = request.getFirstName() != null ? request.getFirstName() : request.getFirstName();
		String lastName = request.getLastName() != null ? request.getLastName() : request.getLastName();
		String address = request.getAddress() != null ? request.getAddress() : request.getAddress();
		String gender = request.getGender() != null ? request.getGender() : request.getGender();
		String emailAddress = request.getEmailAddress() != null ? request.getEmailAddress() : request.getEmailAddress();
		String phoneNumber = request.getPhoneNumber() != null ? request.getPhoneNumber() : request.getPhoneNumber();;
		
		
		if (emailAddress != null && !Utility.isValidEmail(emailAddress)) {
			
			response.setData("");
            response.setMessage("Please enter valid email address");
            response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);

            return response;
		}
		
		if (phoneNumber == null || !Utility.isValidPhone(phoneNumber)) {
			response.setData("");
            response.setMessage("Please enter valid phone number");
            response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);

            return response;
		}
		
		if (firstName == null || firstName.isEmpty()) {
			response.setData("");
            response.setMessage("Please enter first name");
            response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);

            return response;
		}
		
		if (lastName == null || lastName.isEmpty()) {
			response.setData("");
            response.setMessage("Please enter last name");
            response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);

            return response;
		}
		
		if (request.getRole() == null || request.getRole().isEmpty()) {
			response.setData("");
            response.setMessage("Please enter role");
            response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);

            return response;
		}
		
		try {
			
			Users requestUsersEmail = usersRepository.findByEmailAddress(emailAddress);
					
			
			if (requestUsersEmail != null) {
				response.setData("");
                response.setMessage("Email already exist");
                response.setSuccess(false);
                response.setStatus(ServerResponseStatus.FAILED);

                return response;
			}
			
			Users requestUser = usersRepository.findByPhoneNumberOrEmailAddress(phoneNumber, emailAddress);
			
			if (requestUser != null) {
				response.setData("");
                response.setMessage("User email or phone number already exist");
                response.setSuccess(false);
                response.setStatus(ServerResponseStatus.FAILED);

                return response;
			}
			
			
						
			Role role = roleRepository.findByName(request.getRole());
			
			if(role == null){
				response.setData("");
                response.setMessage("Invalid user role");
                response.setSuccess(false);
                response.setStatus(ServerResponseStatus.FAILED);

                return response;
			}
		
			
			users = new Users();
			
			String activationCode = String.valueOf(Utility.generateActivationCode());

			
			users.setRole(role);
			
			users.setFirstName(utility.capitalizeFirstLetter(firstName));
			users.setLastName(utility.capitalizeFirstLetter(lastName));
			users.setAddress(utility.capitalizeFirstLetter(address));
			users.setGender(utility.capitalizeFirstLetter(gender));
			users.setEmailAddress(emailAddress);
			users.setPhoneNumber(phoneNumber);
			users.setDateCreated(new Date());
			users.setActive(false);
			users.setActivationCode(activationCode);
		
			
            
            Mail mail = new Mail();
            mail.setTo(request.getEmailAddress());
            mail.setFrom("idongesitukut25@gmail.com");
            mail.setSubject(" User Account Registeration");

            Map<String, Object> model = new HashMap<String, Object>();
            {
	            model.put("salutation", "Dear " + request.getFirstName());
			}


            model.put("message", "Welcome to Biosec E-Commerce (Shoping) Application and thank you for creating an account. Please confirm your registeration by clicking on the link below to complete your registration process on the system." );
            model.put("link", appConstants.APP_WEB_URL+ "/user/verification/code/" + activationCode);
            mail.setModel(model);
            mail.setTemplate("email_template_link.ftl");

            emailService.sendSimpleMessage(mail);
            
            usersRepository.save(users);
            
	        response.setData(users);
            response.setMessage("User successfully created");
            response.setSuccess(true);
            response.setStatus(ServerResponseStatus.OK);
            

		} catch (Exception e) {
		  response.setData("");
          response.setMessage("Failed to create user account");
          response.setSuccess(false);
          response.setStatus(ServerResponseStatus.FAILED);

          logger.error("An error occured while creating recipient account");
          e.printStackTrace();
		}
		return response;
		
	}

                         

     @Override
      public ServerResponse reSendUserActivation(ResendUserActivationCodeDto request) {
    	 
    	 ServerResponse response = new ServerResponse();
 		
 		try {
 			String emailAddressOrPhoneNumber = request.getEmailAddress() != null ? request.getEmailAddress() : request.getEmailAddress();

 			Users user = findByEmail(emailAddressOrPhoneNumber);
 			
 			if (user == null) {
 				
 				response.setData("");
 		        response.setMessage("User email address not found");
 		        response.setSuccess(false);
 		        response.setStatus(ServerResponseStatus.FAILED);
 				
 				return response;
 			}
 		
 			String activationCode = String.valueOf(Utility.generateActivationCode());

 			user.setActivationCode(activationCode);
 			
 			 Mail mail = new Mail();
             mail.setTo(user.getEmailAddress());
             mail.setFrom("idongesitukut25@gmail.com");
             mail.setSubject("Account verification");

             Map<String, Object> model = new HashMap<String, Object>();
             model.put("salutation", "Dear " + user.getFirstName());
             model.put("message", "We received a request to re-send your activation code, if this is correct, please confirm by clicking on the link below to complete the process on the system. ");
             model.put("link", "https://api.biosecsolutions.com/user/verification/code/" + activationCode);
             mail.setModel(model);
             mail.setTemplate("email_template_link.ftl");
             emailService.sendSimpleMessage(mail);
 			
 			response.setData("");
 	        response.setMessage("Activation code re-sent successfully");
 	        response.setSuccess(true);
 	        response.setData(activationCode);
 	        response.setStatus(ServerResponseStatus.OK);
 			
 		} catch (Exception e) {
 			
 			response.setData("");
 	        response.setMessage("Failed to create user account");
 	        response.setSuccess(false);
 	        response.setStatus(ServerResponseStatus.FAILED);
 	          
 			e.printStackTrace();
 		}
		return response;
 		
     }

     @Override
      public ServerResponse reSendUserPassword(ResendUserPasswordDto request) {
    	 
    	 ServerResponse response = new ServerResponse();
 		
 		try {
 			String emailAddress = request.getEmailAddress() != null ? request.getEmailAddress() : request.getEmailAddress();

 			Users user = findByEmail(emailAddress);
 			
 			if (user == null) {
 				
 				response.setData("");
 		        response.setMessage("User not found");
 		        response.setSuccess(false);
 		        response.setStatus(ServerResponseStatus.FAILED);
 				
 				return response;
 			}
 	        
 			String resetCode = String.valueOf(Utility.generateActivationCode());

 			Users User = entityManager.find(Users.class, user.getUsersId());
			User.setActivationCode(resetCode);
			
			
			Mail mail = new Mail();
            mail.setTo(User.getEmailAddress());
            mail.setFrom("idongesitukut25@gmail.com");
            mail.setSubject("Password Recovery Request");

            Map<String, Object> model = new HashMap<String, Object>();
            model.put("salutation", "Dear " + User.getFirstName());
            model.put("message", "We received a request to send you a password recovery code. <br>Use the password rest code below to set up a new password for your account. If you did not request to reset your password please ignore this email.");
            model.put("link", appConstants.APP_WEB_URL + "/reset-password/" + resetCode);
            mail.setModel(model);
            mail.setTemplate("email_template_link.ftl");
            emailService.sendSimpleMessage(mail);
 			
 			user.setPasswordResetCode(resetCode);
 			
 			response.setData(resetCode);
 	        response.setMessage("password reset code sent successfully");
 	        response.setSuccess(true);
 	        response.setStatus(ServerResponseStatus.OK);
 			
 		} catch (Exception e) {
 			
 			response.setData("");
 	        response.setMessage("Failed to send user password reset code");
 	        response.setSuccess(false);
 	        response.setStatus(ServerResponseStatus.FAILED);
 	          
 			e.printStackTrace();
 		}
 		return response;
 		
      }

     
     
     @Override
      public ServerResponse passwordReset(PasswordResetDto request) {
    	 
    	 ServerResponse response = new ServerResponse();
 		
 		try {
 			String resetCode = request.getResetPassword() != null ? request.getResetPassword() : request.getResetPassword();
 			String password = request.getNewPassword() != null ? request.getNewPassword() : request.getNewPassword();

 			Users user1 = usersRepository.findByActivationCode(resetCode);

 			if (user1 == null) {
 				
 				response.setData("");
 		        response.setMessage("Invalid password reset Code");
 		        response.setSuccess(false);
 		        response.setStatus(ServerResponseStatus.FAILED);
 				
 				return response;
 			}
 			
 			Users User = entityManager.find(Users.class, user1.getUsersId());
 		//	Users User1 = usersRepository.findByPasswordResetCode(request.getResetPassword());
			User.setPassword(passwordEncoder.encode(password));
			User.setActive(true);
			User.setActivationCode(null);
			
			Mail mail = new Mail();
            mail.setTo(User.getEmailAddress());
            mail.setFrom("idongesitukut25@gmail.com");
            mail.setSubject("Password Reset");

            Map<String, Object> model = new HashMap<String, Object>();
            model.put("salutation", "Hello " + User.getFirstName());
            model.put("message", "Your password has been reset successfully. You can now Login to the system with your new password.");
            mail.setModel(model);
            mail.setTemplate("email_template.ftl");
            emailService.sendSimpleMessage(mail);
 			
 			response.setData("User password successfully changed");
 	        response.setMessage("User password successfully changed");
 	        response.setSuccess(true);
 	        response.setStatus(ServerResponseStatus.OK);
 			
 		} catch (Exception e) {
 			
 			response.setData("");
 	        response.setMessage("Failed to reset user password");
 	        response.setSuccess(false);
 	        response.setStatus(ServerResponseStatus.FAILED);
 	          
 			e.printStackTrace();
 		}
 		return response;
        }

     @Override
     public ServerResponse login(SignInRequest request) {
    	 
    	 ServerResponse response = new ServerResponse();
 		try {
 			
 			logger.info(request.getUsername());
 			
 			//convert client id and client secret to base64 token 
 			String authorization = Utility.getCredentials(appConstants.CLIENT_ID, appConstants.CLIENT_SECRET);
 			logger.info(authorization);
 			request.setGrant_type(appConstants.GRANT_TYPE);
 			
 			//send login request
 			response = Utility.loginHttpRequest( appConstants.APP_LOGIN_URL, request, authorization);
 			
 		} catch (Exception e) {
 			response.setData("Something went wrong !!!" + e.getMessage());
 			response.setMessage("User authentication failed");
 			response.setSuccess(false);
 			response.setStatus(ServerResponseStatus.FAILED);
 			
 			return response;
 		}
 		
 		return response;
      }

     @Override
     public ServerResponse userActivation(ActivateUserRequest request) {
    	 
    	 ServerResponse response = new ServerResponse();
 		
 		try {
 			
 			String activationCode = request.getActivationCode() != null ? request.getActivationCode() : request.getActivationCode();
 			String password = request.getPassword() != null ? request.getPassword() : request.getPassword();
 			
 			Users users = usersRepository.findByActivationCode(activationCode);

 			if (users == null) {
 				
 				response.setData("");
 		        response.setMessage("Invalid activation Code");
 		        response.setSuccess(false);
 		        response.setStatus(ServerResponseStatus.FAILED);
 				
 				return response;
 			}

 			users = entityManager.find(Users.class, users.getUsersId());
 			//users = usersRepository.findByEmailAddress(users.getEmailAddress());
 			users.setPassword(passwordEncoder.encode(password));
 			users.setActive(true);
 			users.setActivationCode(null);
 			
 			
 			Mail mail = new Mail();
            mail.setTo(users.getEmailAddress());
            mail.setFrom("idongesitukut25@gmail.com");
            mail.setSubject("Account verification");

            Map<String, Object> model = new HashMap<String, Object>();
            model.put("salutation", "Hello " + users.getFirstName());
            model.put("message", "Your account has been verified. Kindly login into the system.");
            model.put("link", appConstants.APP_LOGIN_URL+ "/");
            mail.setModel(model);
            mail.setTemplate("email_template_link.ftl");

            emailService.sendSimpleMessage(mail);
             
 			
 			response.setData("User successfully activated");
 	        response.setMessage("User successfully activated");
 	        response.setSuccess(true);
 	        response.setStatus(ServerResponseStatus.OK);
 			
 		} catch (Exception e) {
 			
 			response.setData("Something went wrong" + e.getMessage());
 	        response.setMessage("Failed to create user account");
 	        response.setSuccess(false);
 	        response.setStatus(ServerResponseStatus.FAILED);
 	          
 			e.printStackTrace();
 		}
 		return response;
      }

     
     
	   @Override
	    public ServerResponse viewAll() {
		
		ServerResponse response = new ServerResponse();
		
		Collection<Users> users = null;
		
		try {
			
			users = findAll();
			
			if(users == null) {
				response.setData("No user available");	
				response.setStatus(ServerResponseStatus.NO_CONTENT);
				
				return response;
			}
			
			response.setData(users);
			response.setStatus(ServerResponseStatus.OK);
			response.setSuccess(true);
			response.setMessage("Get users successfully");
			
		} catch (Exception e) {
			
			response.setData("Failed to fetch users" + e.getMessage());
			response.setSuccess(false);
			response.setMessage("Failed to fetch users");
			response.setStatus(ServerResponseStatus.FAILED);
			e.printStackTrace();
		}
		
		return response;
	}

	   
	@Override
	public ServerResponse updateUser(UpdateUserRequestDto request) {
		
		ServerResponse response = new ServerResponse();
		Users user = null;
		
		String emailAddress = request.getEmailAddress() != null ? request.getEmailAddress() : request.getEmailAddress();
		String phoneNumber = request.getPhoneNumber() != null ? request.getPhoneNumber() : request.getPhoneNumber();;
		String firstName = request.getFirstName() != null ? request.getFirstName() : request.getFirstName();
		String lastName = request.getLastName() != null ? request.getLastName() : request.getLastName();
		
		if (emailAddress != null && !Utility.isValidEmail(emailAddress)) {
			
			response.setData("");
            response.setMessage("Email address not found");
            response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);

            return response;
		}
		
		if (phoneNumber == null || !Utility.isValidPhone(phoneNumber)) {
			response.setData("");
            response.setMessage("phone number not found");
            response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);

            return response;
		}
		
		try {
			 
			Users userUpdate = findByEmail(request.getEmailAddress());
			
			if(userUpdate == null) {
				response.setData("User not found");
				response.setStatus(ServerResponseStatus.FAILED);
				response.setSuccess(false);
				
				return response;
			}
			
			  user = usersRepository.findByEmailAddress(emailAddress);
			
			  if (firstName != null) 
				  user.setFirstName(firstName);
			   if (lastName != null) 
				   user.setLastName(lastName);
			   if (phoneNumber != null) 
				   user.setPhoneNumber(phoneNumber);
			   if (emailAddress != null)
				   user.setEmailAddress(emailAddress);
			   
			    Mail mail = new Mail();
	            mail.setTo(user.getEmailAddress());
	            mail.setFrom("idongesitukut25@gmail.com");
	            mail.setSubject("Account Update");

	            Map<String, Object> model = new HashMap<String, Object>();
	            model.put("salutation", "Hello " + request.getFirstName());
	            model.put("message", "Your account has been updated. Kindly login into the system to see your updated details.");
	            mail.setModel(model);
	            mail.setTemplate("email_template.ftl");

	            emailService.sendSimpleMessage(mail);
			   
			   
			   usersRepository.save(user);
			   
			   response.setData(user);
			   response.setMessage("User successfully updated");
			   response.setStatus(ServerResponseStatus.UPDATED);
			   response.setSuccess(true);
			
		} catch (Exception e) {
			response.setData("Failed to update user details" + e.getMessage());
			response.setMessage("Failed to update user details");
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.FAILED);
			e.printStackTrace();
			
		}
		
		return response;
	}

	@Override
	public ServerResponse delete(long usersId) {
	
      ServerResponse response = new ServerResponse();
		
		if (usersId == 0) {
			response.setData("code can not be null");
			response.setStatus(ServerResponseStatus.FAILED);
			response.setSuccess(false);
				
			return response;
		}
		
		try {
			
			Users user = usersRepository.findByUsersId(usersId);
			
			
			if (user == null) {
				response.setData("User not found");
				response.setStatus(ServerResponseStatus.FAILED);
				response.setSuccess(false);
				
				return response;
			}
			
			usersRepository.delete(user);
			
			 response.setStatus(ServerResponseStatus.DELETED);
			 response.setMessage("User account has been successfully deleted");
			 response.setData("User account has been successfully deleted");
			 response.setSuccess(true);

	        } catch (Exception e) {
	        	response.setStatus(ServerResponseStatus.FAILED);
	        	response.setData("Failed to delete user account");
	        	response.setSuccess(false);
	            e.printStackTrace();
	        }
		
		return response;
		
	}
		
	
}
