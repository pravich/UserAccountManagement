package com.yggdrasil.account;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.InvalidSearchControlsException;
import javax.naming.directory.InvalidSearchFilterException;
import javax.naming.directory.ModificationItem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LdapModifyUser {
private static Logger logger = LogManager.getLogger(LdapModifyUser.class);
	
	// LDAP Attributes
	private String username; 		// uid, cn
	private String oldpassword; 
	private String password; 		// userpassword
	private String email;			// mail	
	private String firstname; 		// gn
	private String lastname;		// sn
	private String address; 		// postalAddress
	private String zipcode; 		// postalCode
	private String country; 		// c
	private String organization;	// o
	private String mobile; 			// mobile
	private String description;		// description
	
	DirContext dirctx = null;

	Hashtable<String, String> env = null;
	
	LdapModifyUser() {
		createDirContext();
	}
	
	LdapModifyUser(String username, String oldpassword) {
		this();
		this.username = username;
		this.oldpassword = oldpassword;
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
	
	public boolean modifyEntity() {
		if(dirctx == null) 
			return(false);
		
		try {
			ModificationItem[] mods = new ModificationItem[3];
			Attribute modFirstname = new BasicAttribute("gn", firstname);
			Attribute modLastname = new BasicAttribute("sn", lastname);
			Attribute modPassword = new BasicAttribute("userpassword", password);
			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, modFirstname);
			mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, modLastname);
			mods[2] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, modPassword);
			
			dirctx.modifyAttributes("uid=" + username + ",ou=user,dc=yggdrasil,dc=com", mods);
			
			logger.debug("Success modify attributes [" + username + "]");		
			
		} catch(Exception e) {
			logger.error(e.getMessage());
			return(false);
		}
		return(true);
	}
	/**************************/
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOldpassword() {
		return oldpassword;
	}
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
