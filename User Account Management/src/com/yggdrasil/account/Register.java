package com.yggdrasil.account;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Register.doPost()");
		String username  = request.getParameter("username");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		String firstname = request.getParameter("firstname");
		String lastname  = request.getParameter("lastname");
		String email     = request.getParameter("email");
		
		System.out.println("username=" + username);
		System.out.println("password1=" + password1);
		System.out.println("password2=" + password2);
		System.out.println("firstname=" + firstname);
		System.out.println("lastname=" + lastname);
		System.out.println("email=" + email);
		
		if(!password1.equals(password2)) {
			response.sendRedirect("failregister.jsp");
		}
	
		LdapUserRegister userEntity = new LdapUserRegister(username, password1);
		userEntity.setFirstname(firstname);
		userEntity.setLastname(lastname);
		userEntity.setEmail(email);
		if(userEntity.addEntity()) {
			response.sendRedirect("successregister.jsp");
		} else {
			response.sendRedirect("failregister.jsp");
		}
	}

}
