package com.revature.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Reimbursement;
import com.revature.beans.Reimbursement.StatusType;
import com.revature.beans.User.UserType;
import com.revature.beans.User;
import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;
import com.revature.services.ReimbursementServices;
import com.revature.servlets.JSONRequestHelper;

public class FMDecideController {

	public static void decide(HttpServletRequest req, HttpServletResponse res)
			throws JsonProcessingException, IOException {
		ReimbursementDao rDao = new ReimbursementDao();
		ReimbursementServices rServ = new ReimbursementServices(rDao);
		UserDao uDao = new UserDao();
		// check the session and if the user is indeed a FM
		String username = (String) req.getSession(false).getAttribute("username");
		String password = (String) req.getSession(false).getAttribute("password");
		if (username != null) {
			if (password != null) {
				User u = uDao.getUser(username, password);
				if (u.getUser_type().equals(UserType.FINANCEMANAGER)) {
					int id = Integer.parseInt(req.getHeader("ticketId"));
					String decide = req.getHeader("decision");

					Reimbursement r = rDao.getTicket(id);

					switch (decide) {
					case "APPROVE":
						r.setStatus(StatusType.APPROVED);
						r.setResolverId(u.getUser_id());
						rServ.updateTicket(u, r);
						break;

					case "DENY":
						r.setStatus(StatusType.DENIED);
						r.setResolverId(u.getUser_id());
						rServ.updateTicket(u, r);

					}
					JSONRequestHelper.logger.info("FMDecide, updating ticket");

					res.getWriter().write(new ObjectMapper().writeValueAsString("Completed, refresh table to confirm"));

				}
			}
		} else {
			JSONRequestHelper.logger.error("Invalid Credentials in FMView");
			res.getWriter().write(new ObjectMapper().writeValueAsString("INVALID CREDENTIALS"));
		}

	}
}
