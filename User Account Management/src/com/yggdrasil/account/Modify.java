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
 * Servlet implementation class Modify
 */
@WebServlet("/Modify")
public class Modify extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LogManager.getLogger(Modify.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Modify() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username  = request.getParameter("username");
		String oldpassword = request.getParameter("oldpassword");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		String firstname = request.getParameter("firstname");
		String lastname  = request.getParameter("lastname");
		String email     = request.getParameter("email");
		
		logger.debug("username=" + username);
		logger.debug("oldpassword" + oldpassword);
		logger.debug("password1=" + password1);
		logger.debug("password2=" + password2);
		logger.debug("firstname=" + firstname);
		logger.debug("lastname=" + lastname);
		logger.debug("email=" + email);
		
		LdapModifyUser userModify = new LdapModifyUser(username, oldpassword);
		userModify.setFirstname(firstname);
		userModify.setLastname(lastname);
		userModify.setPassword(password1);
		
		if(!userModify.modifyEntity()) {
			logger.error("Modify attribute failed");
			response.sendRedirect("failmodify.jsp");
		} else {
			logger.info("Modify success");
			response.sendRedirect("successmodify.jsp");
		}
		
		

	}

}
