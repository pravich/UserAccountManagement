package com.yggdrasil.account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static Logger logger = LogManager.getLogger(Register.class);
	
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
		String username  = request.getParameter("username");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		String firstname = request.getParameter("firstname");
		String lastname  = request.getParameter("lastname");
		String email     = request.getParameter("email");
		
		logger.debug("username=" + username);
		logger.debug("password1=" + password1);
		logger.debug("password2=" + password2);
		logger.debug("firstname=" + firstname);
		logger.debug("lastname=" + lastname);
		logger.debug("email=" + email);
		
		if(!password1.equals(password2)) {
			logger.debug("Confirmation password doesn't match");
			response.sendRedirect("failregister.jsp");
		}
	
		LdapUserRegister userEntity = new LdapUserRegister(username, password1);
		userEntity.setFirstname(firstname);
		userEntity.setLastname(lastname);
		userEntity.setEmail(email);
		if(userEntity.addEntity()) {
			logger.debug("Successfully register [" + username + "]");
			response.sendRedirect("successregister.jsp");
		} else {
			logger.debug("Registration failed [" + username + "]");
			response.sendRedirect("failregister.jsp");
		}
	}

}
