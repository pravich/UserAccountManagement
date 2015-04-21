package com.yggdrasil.account;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;
import java.util.Hashtable;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Signon
 */
@WebServlet(description = "Signon Servlet", urlPatterns = { "/Signon" })
public class Signon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public Signon() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		// request.getSession(false);
		
		if(!verify(username, password)) {
			response.sendRedirect("invalidlogin.jsp");
		} else {
			// set cookies
			Cookie cookie = new Cookie("s", generateSessionId(request.getParameter("username")));
			cookie.setMaxAge(60*1);
			response.addCookie(cookie);
			
			cookie = new Cookie("ts", new java.util.Date().toString());
			response.addCookie(cookie);
			
			cookie = new Cookie("u", request.getParameter("username"));
			response.addCookie(cookie);
			
			response.sendRedirect("successlogin.jsp");
		}
	}
	
	private boolean verify(String username, String password) {
		if(username == null || password == null) {
			return(false);
		} 
		
//		if(password.length() < 6) {
//			return(false);
//		}
		
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://192.168.10.101:389");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "uid="+ username + ",ou=user,dc=yggdrasil,dc=com");
		env.put(Context.SECURITY_CREDENTIALS, password);
		
		DirContext dc = null;
		
		try {
			// This line below show be enough for verifying against LDAP
			dc = new InitialDirContext(env);
			
			// Bolows are extra
			SearchControls sc = new SearchControls();
			sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
			NamingEnumeration results = dc.search("dc=yggdrasil,dc=com", "uid=" + username, sc);
			while(results.hasMore()) {
				SearchResult sr = (SearchResult) results.next();
				Attributes attributes = sr.getAttributes();
				Attribute mail = attributes.get("mail");
				System.out.println("user email = " + mail);
				//logger.info("user email = " + mail);
			}
			
		} catch(javax.naming.CommunicationException e) {
			System.out.println(e.getMessage());
			//logger.error(e.getMessage());
			return(false);
		} catch(javax.naming.AuthenticationException e) {
			System.out.println(e.getMessage());
			//logger.error(e.getMessage());
			return(false);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			//logger.error(e.getMessage());
			return(false);
		}
		
		return(true);
	}
	
	private String generateSessionId(String key) {
		String sessionId = null;
		try {
			sessionId = calculateRFC2104HMAC(new java.util.Date() + key, "DAAADF9CE77AF565D03753A2D2");
		} catch (Exception e) {
			System.out.println("generateSessionId() failed");
			//logger.error("generateSessionId() failed");
		}
		return(sessionId);
	}

	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	
	private static String toHexString(byte[] bytes) {
		Formatter formatter = new Formatter(); 

		for (byte b : bytes) {
			formatter.format("%02x", b);
		}

		return(formatter.toString());
	}

	private String calculateRFC2104HMAC(String data, String key) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
		Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		mac.init(signingKey);
		return(toHexString(mac.doFinal(data.getBytes())));
	}
}
