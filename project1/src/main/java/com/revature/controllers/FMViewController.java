package com.revature.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Reimbursement;
import com.revature.beans.User;
import com.revature.beans.User.UserType;
import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;
import com.revature.servlets.JSONRequestHelper;

public class FMViewController {

	public static void display(HttpServletRequest req, HttpServletResponse res, String filter)
			throws JsonProcessingException, IOException {
		UserDao uDao = new UserDao();
		// check the session and if the user is indeed a FM
		
		
		if (req.getSession().getAttribute("username") != null) {
			String username = (String) req.getSession(false).getAttribute("username");
			if (req.getSession().getAttribute("password") != null) {
				String password = (String) req.getSession(false).getAttribute("password");
				User u = uDao.getUser(username, password);
				if (u.getUser_type().equals(UserType.FINANCEMANAGER)) {
					ReimbursementDao rDao = new ReimbursementDao();
					ArrayList<Reimbursement> table = new ArrayList<Reimbursement>();
					
					switch (filter) {
					case "ALL":
						table = (ArrayList<Reimbursement>) rDao.getAllTickets();
						break;

					case "PENDING":
						table = (ArrayList<Reimbursement>) rDao.getPending();
						break;

					case "APPROVED":
						table = (ArrayList<Reimbursement>) rDao.getApproved();
						break;

					case "DENIED":
						table = (ArrayList<Reimbursement>) rDao.getDenied();
						break;

					}
					JSONRequestHelper.logger.info("Sending requested Table");
					res.getWriter().write(new ObjectMapper().writeValueAsString(table));
				} else {
					JSONRequestHelper.logger.error("Invalid Credentials in FMView");
					res.getWriter().write(new ObjectMapper().writeValueAsString("Invalid Credentials"));
				}
			} else {
				JSONRequestHelper.logger.error("Invalid Credentials in FMView");
				res.getWriter().write(new ObjectMapper().writeValueAsString("Invalid Credentials"));
			}
		} else {
			JSONRequestHelper.logger.error("Invalid Credentials in FMView");
			res.getWriter().write(new ObjectMapper().writeValueAsString("Invalid Credentials"));
		}

	}

}
