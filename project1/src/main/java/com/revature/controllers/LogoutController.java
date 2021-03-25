package com.revature.controllers;

import javax.servlet.http.HttpServletRequest;

import com.revature.servlets.RequestHelper;

public class LogoutController {
	
	public static String logout(HttpServletRequest req) {
		req.getSession(false).setAttribute("username", null);
		req.getSession(false).setAttribute("password", null);
		req.getSession().invalidate();
		RequestHelper.logger.info("Logging out, sending to Home page");
		return "html/index.html";
	}

}
