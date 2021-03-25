package com.revature.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Reimbursement;
import com.revature.beans.User;
import com.revature.beans.Reimbursement.ReimbursementType;
import com.revature.beans.Reimbursement.StatusType;
import com.revature.beans.User.UserType;
import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;
import com.revature.services.ReimbursementServices;
import com.revature.servlets.JSONRequestHelper;

public class EMRequestController {
	
	public static void request(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException {
		ReimbursementDao rDao = new ReimbursementDao();
		ReimbursementServices rServ = new ReimbursementServices(rDao);
		UserDao uDao = new UserDao();
		// check the session and if the user is indeed a FM
		String username = (String) req.getSession(false).getAttribute("username");
		String password = (String) req.getSession(false).getAttribute("password");
		if (username != null) {
			if (password != null) {
				User u = uDao.getUser(username, password);
				if (u.getUser_type().equals(UserType.EMPLOYEE)) {
					double amount = Double.parseDouble(req.getHeader("amount"));
					String description = req.getHeader("description");
					String type = req.getHeader("type");
					Reimbursement ticket = new Reimbursement();
					ticket.setAuthorId(u.getUser_id());
					ticket.setAmount(amount);
					ticket.setDescription(description);
					if(type.equals("FOOD")) ticket.setType(ReimbursementType.FOOD);
					if(type.equals("LODGING")) ticket.setType(ReimbursementType.LODGING);
					if(type.equals("TRAVEL")) ticket.setType(ReimbursementType.TRAVEL);
					if(type.equals("OTHER")) ticket.setType(ReimbursementType.OTHER);
					rServ.createNewTicket(ticket);
					JSONRequestHelper.logger.info("Employee submitted new ticket");
					res.getWriter().write(new ObjectMapper().writeValueAsString("Completed, refresh table to confirm"));

				} else {
					JSONRequestHelper.logger.error("Invalid Credentials in EMRequest");
					res.getWriter().write(new ObjectMapper().writeValueAsString("INVALID CREDENTIALS"));
				}
			} else {
				JSONRequestHelper.logger.error("Invalid Credentials in EMRequest");
				res.getWriter().write(new ObjectMapper().writeValueAsString("INVALID CREDENTIALS"));
			}
		} else {
			JSONRequestHelper.logger.error("Invalid Credentials in EMRequest");
			res.getWriter().write(new ObjectMapper().writeValueAsString("INVALID CREDENTIALS"));
		}
		
		
	}

}
