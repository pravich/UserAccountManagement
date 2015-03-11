package com.yggdrasil.account;

import java.io.IOException;
import java.io.PrintWriter;

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
		Cookie cookie = new Cookie("session", "abcd");
		cookie.setMaxAge(60*1);
		response.addCookie(cookie);
		writer.println("a cookie created");
		
		cookie=new Cookie("timestamp", new java.util.Date() + "");
		response.addCookie(cookie);
		writer.close();
	}

}
