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

public class LdapUserRegister {
	// Logger
	private static Logger logger = LogManager.getLogger(LdapUserRegister.class);
	
	// LDAP Attributes
	private String username; 		// uid, cn
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
	
	// Should be loaded from configuration file
	protected String initialContextFactory = "com.sun.jndi.ldap.LdapCtxFactory";
	//String providerUrl = "ldap://192.168.10.101:389";
	protected String providerUrl = "ldap://10.211.55.101:389";
	protected String securityAuthentication = "simple";
	protected String securityPrincipal = "uid=admin,ou=staff,dc=yggdrasil,dc=com";
	protected String securityCredential = "admin";
	
	Hashtable<String, String> env = null;
	
	
	/*** Constructor ***/
	LdapUserRegister() {
		env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactory);
		env.put(Context.PROVIDER_URL, providerUrl);
		env.put(Context.SECURITY_AUTHENTICATION, securityAuthentication);
		env.put(Context.SECURITY_PRINCIPAL, securityPrincipal);
		env.put(Context.SECURITY_CREDENTIALS, securityCredential);
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
	
	LdapUserRegister(String username, String password) {
		this();
		this.username = username;
		this.password = password;
	}
	
	public boolean addEntity() {
		if(dirctx == null)
			return(false);
		
		try {
			Attributes userattributes = new BasicAttributes(true);
			
			userattributes.put(new BasicAttribute("objectClass", "top"));
			userattributes.put(new BasicAttribute("objectClass", "person"));
			userattributes.put(new BasicAttribute("objectClass", "organizatonPerson"));
			userattributes.put(new BasicAttribute("objectClass", "inetorgperson"));
			
			userattributes.put( new BasicAttribute("uid", username));
			userattributes.put( new BasicAttribute("cn", username));
			userattributes.put( new BasicAttribute("userpassword", password));			
			userattributes.put( new BasicAttribute("mail", email));
			userattributes.put( new BasicAttribute("gn", firstname));
			userattributes.put( new BasicAttribute("sn", lastname));
			
			if(address != null)
				userattributes.put( new BasicAttribute("postalAddress", address));
			
			if(zipcode != null)
				userattributes.put( new BasicAttribute("postalCode", zipcode));
			
			if(country != null)
				userattributes.put( new BasicAttribute("c", country));
			
			if(organization != null)
				userattributes.put( new BasicAttribute("o", organization));
			
			if(mobile != null) 
				userattributes.put( new BasicAttribute("mobile", mobile));
			if(description != null)
				userattributes.put( new BasicAttribute("description", description));
			
			String dn = "uid=" + username + ",ou=user,dc=yggdrasil,dc=com";
			
			dirctx.bind(dn, dirctx, userattributes);
		} catch(Exception e) {
			logger.error(e.getMessage());
			return(false);
		}
		
		return(true);
	}
	
	// Getters and Setters
		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
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
