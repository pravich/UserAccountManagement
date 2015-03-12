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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet()");
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html");
		writer.println("<h1>doGet<h1>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost()");
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html");
		writer.println("<h1>doPost<h1>");
		writer.println("<h2>Date: " + new java.util.Date() + " <h2>");
		writer.println("<h3>User Name: " + request.getParameter("username") + " </h3>");
		writer.println("<h3>Password: " + request.getParameter("password") + " </h3>");
		
		// read cookies
		Cookie[] requestCookies = request.getCookies();
		for(Cookie c : requestCookies) {
			writer.write("<h3>----------------------------------------------</h3>");
			writer.write("<h3>Cookie Name:" + c.getName() + "</h3>");
			writer.write("<h3>Cookie Value:" + c.getValue() + "</h3>");
			writer.write("<h3>Cookie Comment:" + c.getComment() + "</h3>");
			writer.write("<h3>Cookie Domain:" + c.getMaxAge() + "</h3>");
			writer.write("<h3>Cookie Path:" + c.getPath() + "</h3>");
			writer.write("<h3>Cookie Version:" + c.getVersion() + "</h3>");
		}
		
		// set cookies
		Cookie cookie = new Cookie("session", generateSessionId(request.getParameter("username")));
		cookie.setMaxAge(60*1);
		response.addCookie(cookie);
		writer.println("a cookie created");
		
		cookie=new Cookie("timestamp", new java.util.Date() + "");
		response.addCookie(cookie);
		
		cookie=new Cookie("user", request.getParameter("username"));
		response.addCookie(cookie);
		writer.close();
	}
	
	private String generateSessionId(String key) {
		String sessionId = null;
		try {
			sessionId = calculateRFC2104HMAC(new java.util.Date() + key, "DEADBEEF");
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
