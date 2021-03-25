package com.revature.controllers;

import javax.servlet.http.HttpServletRequest;

import com.revature.beans.User;
import com.revature.beans.User.UserType;
import com.revature.dao.UserDao;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.services.UserServices;
import com.revature.servlets.RequestHelper;

public class RegisterController {
	
	public static String create(HttpServletRequest req) {
		if(!req.getMethod().equals("POST")) {
			RequestHelper.logger.error("Get request attempted instead of post");
			return "resources/html/index.html";
		}
		UserDao uDao = new UserDao();
		UserServices uServ = new UserServices(uDao);
		User u = new User();
		u.setUsername(req.getParameter("username"));
		u.setPassword(req.getParameter("password"));
		u.setFirst_name(req.getParameter("firstname"));
		u.setLast_name(req.getParameter("lastname"));
		u.setEmail(req.getParameter("email"));
		if(req.getParameter("type").equals("financemanager")) {
			u.setUser_type(UserType.FINANCEMANAGER);
		} else {
			u.setUser_type(UserType.EMPLOYEE);
		}
		try {
			uServ.register(u);
			RequestHelper.logger.info("New user registered");
			return "/html/index.html";
		} catch(UsernameAlreadyExistsException e) {
			e.printStackTrace();
			RequestHelper.logger.error("Registration failed, Username already exists");
			return "failedtoregister.change";
		}
	}

}
