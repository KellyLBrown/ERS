package com.revature.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.controllers.EMRequestController;
import com.revature.controllers.EMViewController;
import com.revature.controllers.EmployeeHomeController;
import com.revature.controllers.FMDecideController;
import com.revature.controllers.FMViewController;
import com.revature.controllers.FinanceManagerHomeController;
import com.revature.controllers.LoginController;
import com.revature.controllers.LogoutController;
import com.revature.controllers.RegisterController;

public class RequestHelper {
	
	public final static Logger logger = Logger.getLogger(RequestHelper.class);
	
	
	
	public static String process(HttpServletRequest req, HttpServletResponse res) throws IOException {
		logger.setLevel(Level.ALL);
		
		switch(req.getRequestURI()) {
		case "/project1/login.change":
			logger.info("In login.change helper");
			return LoginController.login(req);
			
		case "/project1/register.change":
			logger.info("In register.change helper");
			return RegisterController.create(req);
			
		case "/project1/emhome.change":
			logger.info("In emhome.change helper");
			return EmployeeHomeController.home(req);
			
		case "/project1/fmhome.change":
			logger.info("In fmhome.change helper");
			return FinanceManagerHomeController.home(req);
			
		case "/project1/logout.change":
			logger.info("In logout.change helper");
			return LogoutController.logout(req);
			
		default:
			logger.error("In default");
			return "/html/unsuccessful.html";
		
		}
	}

}
