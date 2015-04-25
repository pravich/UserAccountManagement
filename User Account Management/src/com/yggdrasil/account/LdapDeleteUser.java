package com.yggdrasil.account;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.InvalidSearchControlsException;
import javax.naming.directory.InvalidSearchFilterException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LdapDeleteUser {
	// Logger
	private static Logger logger = LogManager.getLogger(LdapUserRegister.class);
	
	// LDAP Attributes
	private String username; 		// uid, cn
	
	DirContext dirctx = null;

	Hashtable<String, String> env = null;
	
	LdapDeleteUser() {
		createDirContext();
	}
	
	LdapDeleteUser(String username) {
		this();
		this.username = username;
	}

	private void createDirContext() {
		env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, Configuration.providerUrl);
		env.put(Context.SECURITY_AUTHENTICATION, Configuration.securityAuthentication);
		env.put(Context.SECURITY_PRINCIPAL, Configuration.securityPrincipal);
		env.put(Context.SECURITY_CREDENTIALS, Configuration.securityCredential);
		try {
			dirctx = new InitialDirContext(env);
		} catch(InvalidSearchControlsException e) {
			logger.error(e.getMessage());
		} catch(InvalidSearchFilterException e) {
			logger.error(e.getMessage());
		} catch(NamingException e) {
			logger.error(e.getMessage());
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public boolean deleteEntity() {
		if(dirctx == null)
			return(false);
		
		try {
			dirctx.destroySubcontext("uid=" + username + ",ou=user,dc=yggdrasil,dc=com");
			
			logger.info("delete user=" + username + " successfully");
		} catch(Exception e) {
			logger.error(e.getMessage());
			return(false);
		}
		
		return(true);
	}
}
