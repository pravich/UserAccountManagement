package com.yggdrasil.account;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
		writer.println(request.getParameter("username"));
		writer.println(request.getParameter("password"));
		writer.close();
	}

}
