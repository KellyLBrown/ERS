package com.revature.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Reimbursement;
import com.revature.beans.User;
import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;
import com.revature.servlets.JSONRequestHelper;

public class EMViewController {
	
	public static void display(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException {
		ReimbursementDao rDao = new ReimbursementDao();
		HttpSession sesh = req.getSession();
		String uname = sesh.getAttribute("username").toString();
		String pass = sesh.getAttribute("password").toString();
		UserDao uDao = new UserDao();
		User u = uDao.getUser(uname, pass);
		ArrayList<Reimbursement> table = (ArrayList<Reimbursement>) rDao.getTicketsByAuthor(u.getUser_id());
		
		
		JSONRequestHelper.logger.info("Sending Employees Ticket table");
		res.getWriter().write(new ObjectMapper().writeValueAsString(table));
		
	}

}
