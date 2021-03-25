package com.revature.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.beans.User;
import com.revature.beans.User.UserType;
import com.revature.dao.UserDao;
import com.revature.exceptions.InvalidCredentialsException;
import com.revature.services.UserServices;
import com.revature.servlets.RequestHelper;

public class LoginController {
	
	public static String login(HttpServletRequest req) throws IOException {
		if(!req.getMethod().equals("POST")) {
			RequestHelper.logger.error("Get attempted for login instead of post");
			return "resources/html/index.html";
			
		} 
		UserDao uDao = new UserDao();
		UserServices uServ = new UserServices(uDao);
		
		
		try {
			uServ.login(req.getParameter("username"), req.getParameter("password"));
			req.getSession().setAttribute("username", req.getParameter("username"));
			req.getSession().setAttribute("password", req.getParameter("password"));
			User u = uDao.getUser(req.getParameter("username"), req.getParameter("password"));
			if(u.getUser_type().equals(UserType.FINANCEMANAGER)) {
				RequestHelper.logger.info("Financial Manager Logged in");
				return "fmhome.change";
			} else {
				RequestHelper.logger.info("Employee Logged in");
				return "emhome.change";
			}
		} catch(InvalidCredentialsException e) {
			e.printStackTrace();
			RequestHelper.logger.error("Invalid Credentials");
			return "wrongcreds.change";
		}
		
		}
}

