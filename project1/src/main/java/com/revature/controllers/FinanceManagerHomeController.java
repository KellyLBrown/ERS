package com.revature.controllers;

import javax.servlet.http.HttpServletRequest;

import com.revature.beans.User;
import com.revature.beans.User.UserType;
import com.revature.dao.UserDao;
import com.revature.servlets.RequestHelper;

public class FinanceManagerHomeController {
	
	public static String home(HttpServletRequest req) {
		// check the session and if the user is indeed a FM
		UserDao uDao = new UserDao();
		String username = (String) req.getSession(false).getAttribute("username");
		String password = (String) req.getSession(false).getAttribute("password");
		if(username != null) {
			if(password != null) {
				User u = uDao.getUser(username, password);
				if(u.getUser_type().equals(UserType.FINANCEMANAGER)) {
					RequestHelper.logger.info("Returning Finance Manager Home");
					return "/html/fmpage.html";
				} else {
					RequestHelper.logger.error("Invalid session, returning to homepage");
					return "/html/index.html";
				}
			} else {
				RequestHelper.logger.error("Invalid session, returning to homepage");
				return "/html/index.html";
			}
			
		} else {
			RequestHelper.logger.error("Invalid session, returning to homepage");
			return "/html/index.html";
		}
		
		
		
	}

}
