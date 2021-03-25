package com.revature.controllers;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.servlets.RequestHelper;



public class EmployeeHomeController {
	
	public static String home(HttpServletRequest req) throws IOException {
		HttpSession session = req.getSession(false);
		//allow access only if session exists
		if(session.getAttribute("username") == null){
			RequestHelper.logger.info("Returning to homepage, invalid seesion");
			return "html/index.html";
		}
		RequestHelper.logger.info("Returning Employee Home");
		return "/html/empage.html";
		
	}

}
