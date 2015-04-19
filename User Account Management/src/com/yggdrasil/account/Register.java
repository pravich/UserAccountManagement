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
		
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://192.168.10.101:389");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=staff,dc=yggdrasil,dc=com");
		env.put(Context.SECURITY_CREDENTIALS, "admin");
		
		try {
			DirContext dc = null;
			dc = new InitialDirContext(env);
			
			Attributes attrs = new BasicAttributes(true);
			attrs.put(new BasicAttribute("uid", username));
			attrs.put(new BasicAttribute("cn", username));
			attrs.put(new BasicAttribute("street", "na"));
			attrs.put(new BasicAttribute("sn", lastname));
			attrs.put(new BasicAttribute("userpassword", password1));
			attrs.put(new BasicAttribute("objectclass", "top"));
			attrs.put(new BasicAttribute("objectclass", "person"));
			attrs.put(new BasicAttribute("objectclass", "organizationalPerson"));
			attrs.put(new BasicAttribute("objectclass", "inetorgperson"));
			String dn = "uid=" + username + ",ou=user,dc=yggdrasil,dc=com";
			InitialDirContext iniDirContext = (InitialDirContext)dc;
			iniDirContext.bind(dn, dc, attrs);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
