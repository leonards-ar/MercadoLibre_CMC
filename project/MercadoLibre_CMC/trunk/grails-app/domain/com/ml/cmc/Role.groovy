package com.ml.cmc

class Role {

	String authority

	static mapping = {
		cache true
		table 'A_ROLE'
		version false
	}

	static constraints = {
		authority blank: false, unique: true
	}
	
}
