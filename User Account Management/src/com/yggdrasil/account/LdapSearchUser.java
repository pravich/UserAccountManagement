package com.yggdrasil.account;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.InvalidSearchControlsException;
import javax.naming.directory.InvalidSearchFilterException;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LdapSearchUser {

	private static Logger logger = LogManager.getLogger(LdapSearchUser.class);
	
	private String username; 		// uid, cn
	
	DirContext dirctx = null;

	Hashtable<String, String> env = null;
	
	LdapSearchUser() {
		createDirContext();
	}
	
	LdapSearchUser(String username) {
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
	
	public boolean searchUser() {
		if(dirctx == null) 
			return(false);

		String base = "ou=user,dc=yggdrasil,dc=com";
		boolean found = false;

		try {
			SearchControls sc = new SearchControls();
			sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
			
			String filter = "(&(objectClass=person)(uid=" + username + "))";
			NamingEnumeration results = dirctx.search(base, filter, sc);
			
			while(results.hasMore()) {
				SearchResult sr = (SearchResult) results.next();
				Attributes attrs = sr.getAttributes();
				
				Attribute attr = attrs.get("uid");
				if(attr != null) {
					found = true;
					logger.debug("record found " + attr.get());
				}
			}
			logger.debug("Success modify attributes [" + username + "]");		
			dirctx.close();
		} catch(Exception e) {
			logger.error(e.getMessage());
			return(false);
		}
		return(found);
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
