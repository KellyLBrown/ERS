package com.revature.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.controllers.EMRequestController;
import com.revature.controllers.EMViewController;
import com.revature.controllers.FMDecideController;
import com.revature.controllers.FMViewController;
import com.revature.controllers.SessionChecker;

public class JSONRequestHelper {
	public final static Logger logger = Logger.getLogger(JSONRequestHelper.class);
	
	public static void process(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException{
		logger.setLevel(Level.ALL);
		switch(req.getRequestURI()) {
		case "/project1/ALLfmview.json":
			logger.info("In All fmview.json helper");
			FMViewController.display(req, res, "ALL");
			break;
			
		case "/project1/PENDINGfmview.json":
			logger.info("In pending fmview.json helper");
			FMViewController.display(req, res, "PENDING");
			break;
			
		case "/project1/APPROVEDfmview.json":
			logger.info("In Approved fmview.json helper");
			FMViewController.display(req, res, "APPROVED");
			break;
			
		case "/project1/DENIEDfmview.json":
			logger.info("In Denied fmview.json helper");
			FMViewController.display(req, res, "DENIED");
			break;
			
		case "/project1/fmdecide.json":
			logger.info("In fmdecide.json helper");
			FMDecideController.decide(req, res);
			break;
			
		case "/project1/emview.json":
			logger.info("In emview.json helper");
			EMViewController.display(req, res);
			break;
			
		case "/project1/emrequest.json":
			logger.info("In emrequest.json helper");
			EMRequestController.request(req, res);
			break;
			
		case "/project1/sessioncheck.json":
			logger.info("In sessioncheck.json helper");
			SessionChecker.check(req, res);
			break;
		}
		
	}

}
