package com.yggdrasil.account;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class Authentication {
	public Authentication() {
		
	}
	
	@WebMethod
	public String signon(String username, String password) {
		return(null);
	}

}
