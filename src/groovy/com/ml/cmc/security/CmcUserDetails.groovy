package com.ml.cmc.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class CmcUserDetails extends User {

	// extra instance variables 
	final String fullname 
	final String email 
	final String displayName
	
	CmcUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<GrantedAuthority> authorities, String fullname, String email, String title) {

		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities)
		
		this.fullname = fullname
		this.email = email
		  
	}
}
