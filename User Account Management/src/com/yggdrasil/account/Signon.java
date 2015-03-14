package com.yggdrasil.account;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
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
		
		if(!verify(username, password)) {
			response.sendRedirect("invalidlogin.jsp");
		}
		
		// set cookies
		Cookie cookie = new Cookie("session", generateSessionId(request.getParameter("username")));
		cookie.setMaxAge(60*1);
		response.addCookie(cookie);
		
		cookie = new Cookie("timestamp", new java.util.Date().toString());
		response.addCookie(cookie);
		
		cookie = new Cookie("user", request.getParameter("username"));
		response.addCookie(cookie);
		
		response.sendRedirect("successlogin.jsp");
	}
	
	private boolean verify(String username, String password) {
		if(username == null || password == null) {
			return(false);
		} 
		
		if(password.length() < 6) {
			return(false);
		}
		
		String passwordHash = null;
		
		try {
			passwordHash = calculateRFC2104HMAC(password, "marktwain");
		} catch(Exception e) {
			System.out.println("Password hashing error!");
			return(false);
		}
		
		// retrieve password hash from LDAP
		
		return(true);
	}
	
	private String generateSessionId(String key) {
		String sessionId = null;
		try {
			sessionId = calculateRFC2104HMAC(new java.util.Date() + key, "DAAADF9CE77AF565D03753A2D2");
		} catch (Exception e) {
			System.out.println("generateSessionId() failed");
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
