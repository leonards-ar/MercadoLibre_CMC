package com.ml.cmc.security

import org.springframework.ldap.core.DirContextAdapter
import org.springframework.ldap.core.DirContextOperations
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper
import org.springframework.security.core.userdetails.UserDetails


class CmcUserDetailsContextMapper implements UserDetailsContextMapper {

	UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection authorities) {

		
		String fullname = ctx.originalAttrs.attrs['name'].values[0] 
		String mail = "some@mail.com"//ctx.originalAttrs.attrs['mail'].values[0].toString().toLowerCase()
		String title = ctx.originalAttrs.attrs['sn']


		def userDetails = new CmcUserDetails(username, '', true, true, true, true, authorities, fullname, mail, title == null ? '' : title) 
		
		return userDetails
	}
		
	void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
		throw new IllegalStateException("Only retrieving data from AD is currently supported")
	}
	
}
