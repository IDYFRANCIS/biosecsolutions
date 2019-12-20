package com.francis.biosectest;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.francis.biosectest.constants.AppConstants;
import com.francis.biosectest.model.Privilege;
import com.francis.biosectest.model.Role;
import com.francis.biosectest.model.Users;
import com.francis.biosectest.repository.PrivilegeRepository;
import com.francis.biosectest.repository.RoleRepository;
import com.francis.biosectest.repository.UsersRepository;
import com.francis.biosectest.service.PrivilegeService;
import com.francis.biosectest.service.RoleService;
import com.francis.biosectest.service.UsersService;


@Transactional
@Component
public class InitialDataLoader  implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private AppConstants appConstants;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private UsersService usersService;

	@Autowired
	private PrivilegeService privilegeService;

	@Autowired
	private PrivilegeRepository privilegeRepository;

	@Autowired
	private RoleService roleService;

	@Autowired
	private RoleRepository roleRepository;

	
	private boolean hasBeenSetup;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	private static Logger logger = LogManager.getLogger(InitialDataLoader.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (hasBeenSetup) {
			return;
		}

		createPrivilagesIfNotFound();
		createRolesIfNotFound();
		createSuperAdminAccountIfNotFound();
		
		
		hasBeenSetup = true;
	}

	private Privilege createPrivilagesIfNotFound() {

		Privilege privileges;
		Privilege create = privilegeService.findByName("CREATE");
		Privilege upadte = privilegeService.findByName("UPDATE");
		Privilege delete = privilegeService.findByName("DELETE");
		Privilege read = privilegeService.findByName("READ");

		if (create == null) {
			privileges = new Privilege();
			privileges.setName("CREATE");
			privilegeRepository.save(privileges);
		}

		if (upadte == null) {
			privileges = new Privilege();
			privileges.setName("UPDATE");
			privilegeRepository.save(privileges);
		}
		if (delete == null) {
			privileges = new Privilege();
			privileges.setName("DELETE");
			privilegeRepository.save(privileges);
		}

		if (read == null) {
			privileges = new Privilege();
			privileges.setName("READ");
			privilegeRepository.save(privileges);
		}

		return null;
	}

	
	private void createRolesIfNotFound() {

		Role superAdminRole = roleService.findByName("SUPER_ADMIN");
		Role adminRole = roleService.findByName("ADMIN");
		Role userRole = roleService.findByName("USER");
		Role storeOfficerRole = roleService.findByName("STORE_OFFICER");
		
		Privilege create = privilegeService.findByName("CREATE");
		Privilege update = privilegeService.findByName("UPDATE");
		Privilege delete = privilegeService.findByName("DELETE");
		Privilege read = privilegeService.findByName("READ");
		
		Collection<Privilege> SuperAdminPrivileges = new HashSet<>();
		SuperAdminPrivileges.add(create);
		SuperAdminPrivileges.add(update);
		SuperAdminPrivileges.add(delete);
		SuperAdminPrivileges.add(read);
		
		Collection<Privilege> AdminPrivileges = new HashSet<>();
		AdminPrivileges.add(create);
		AdminPrivileges.add(update);
		AdminPrivileges.add(read);
		
		Collection<Privilege> UserPrivileges = new HashSet<>();
		//userPrivileges.add(update);
		UserPrivileges.add(read);
		
		Collection<Privilege> storeOfficerPrivileges = new HashSet<>();
	//	storeOfficerPrivileges.add(update);
		storeOfficerPrivileges.add(read);
		
		if (superAdminRole == null) {
			superAdminRole = new Role();
			superAdminRole.setName("SUPER_ADMIN");
			superAdminRole.setPrivileges(SuperAdminPrivileges);
			roleRepository.save(superAdminRole);
		}

		if (userRole == null) {
			userRole = new Role();
			userRole.setName("USER");
			userRole.setPrivileges(UserPrivileges);
			roleRepository.save(userRole);
		}
		
		if (adminRole == null) {
			adminRole = new Role();
			adminRole.setName("ADMIN");
			adminRole.setPrivileges(AdminPrivileges);
			roleRepository.save(adminRole);
		}
		
		
		if (storeOfficerRole == null) {
			storeOfficerRole = new Role();
			storeOfficerRole.setName("STORE_OFFICER");
			storeOfficerRole.setPrivileges(storeOfficerPrivileges);
			roleRepository.save(storeOfficerRole);
		}

		
	}

	private Users createSuperAdminAccountIfNotFound() {

		Users users = usersService.findByEmail(appConstants.APP_ADMIN_EMAIL);
		Role superAdmin = roleRepository.findByName("SUPER_ADMIN");
		
		logger.info("Starting to create super-admin account ");

		if (users != null) {
			return null;
		}

		Users user = new Users();
		user.setActive(true);
		user.setEmailAddress(appConstants.APP_ADMIN_EMAIL);
		user.setPhoneNumber(appConstants.APP_DEFAULT_ADMIN_PHONE);
		user.setFirstName(appConstants.APP_DEFAULT_ADMIN_NAME);
		user.setLastName(appConstants.APP_DEFAULT_ADMIN_NAME);
		user.setPassword(passwordEncoder.encode(appConstants.APP_ADMIN_PASSWORD));
		user.setRole(superAdmin);
		user.setFirstName(appConstants.APP_DEFAULT_ADMIN_NAME);

		logger.info("Admin Account " + user.getEmailAddress());
		
		Users findEmail = usersRepository.findByEmailAddress(user.getEmailAddress());

		if (findEmail != null) {
			return null;
		}

		usersRepository.save(user);

		return user;
	}

	


}
