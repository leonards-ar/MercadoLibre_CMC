package com.ml.cmc.security

import org.springframework.ldap.core.DirContextAdapter
import org.springframework.ldap.core.DirContextOperations
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper
import org.springframework.security.core.userdetails.UserDetails


class CmcUserDetailsContextMapper implements UserDetailsContextMapper {

	UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection authorities) {

		//'name', 'displayName', 'userPrincipalName', 'sn', 'primaryGroupID'
		String fullname = ctx.originalAttrs.attrs['name'].values[0] 
		String displayName = ctx.originalAttrs.attrs['displayName'].values[0]
		String mail = ctx.originalAttrs.attrs['userPrincipalName'].values[0]
		String title = ctx.originalAttrs.attrs['sn']


		def userDetails = new CmcUserDetails(username, null, true, true, true, true, authorities, fullname, displayName, mail, title == null ? '' : title.values[0]) 
		
		return userDetails
	}
		
	void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
		throw new IllegalStateException("Only retrieving data from AD is currently supported")
	}
	
}
