package com.francis.biosectest.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignInRequest implements Serializable{
	
	   @JsonIgnore
	    private String grant_type;
		
	    private String username;
	    
	    private String password;
	    
	    

		public String getGrant_type() {
			return grant_type;
		}

		public void setGrant_type(String grant_type) {
			this.grant_type = grant_type;
		}

		
		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}


	    
	    
}
