package com.yggdrasil.account;

import java.io.IOException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
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
		System.out.println("username=" + request.getParameter("username"));
		System.out.println("password1=" + request.getParameter("password1"));
		System.out.println("password2=" + request.getParameter("password2"));
		System.out.println("firstname=" + request.getParameter("firstname"));
		System.out.println("lastname=" + request.getParameter("lastname"));
		System.out.println("email=" + request.getParameter("email"));
		
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://192.168.10.101:389");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=staff,dc=yggdrasil,dc=com");
		env.put(Context.SECURITY_CREDENTIALS, "admin");
		
		DirContext dc = null;
		
		try {
			dc = new InitialDirContext(env);
			
			BasicAttribute basicAttribute = new BasicAttribute("objectClass");
			basicAttribute.add("person");
			basicAttribute.add("user");
			
			Attributes userAttributes = new BasicAttributes();
			userAttributes.put(basicAttribute);
			
			userAttributes.put("givenName", request.getParameter("firstname"));
			userAttributes.put("sn", request.getParameter("lastname"));
			userAttributes.put("cn", request.getParameter("username"));
			userAttributes.put("uid", request.getParameter("username"));
			userAttributes.put("mail", request.getParameter("email"));
			userAttributes.put("userpassword", request.getParameter("password1"));
			String name = "uid=" + request.getParameter("username") + ",ou=user,dc=yggdrasil,dc=com";
			
			InitialDirContext iniDirContext = (InitialDirContext) dc; 
			iniDirContext.bind(name,  dc, userAttributes);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
