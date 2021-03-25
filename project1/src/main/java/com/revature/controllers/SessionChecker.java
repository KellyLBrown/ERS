package com.revature.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.User;
import com.revature.dao.UserDao;
import com.revature.servlets.JSONRequestHelper;

public class SessionChecker {

	public static void check(HttpServletRequest req, HttpServletResponse res)
			throws JsonProcessingException, IOException {
		// check the session and if the user is indeed a FM
		HttpSession sesh = req.getSession(false);
		if (sesh != null) {
			if (sesh.getAttribute("username") != null) {
				String username = (String) sesh.getAttribute("username");
				if (sesh.getAttribute("password") != null) {
					String password = (String) sesh.getAttribute("password");
					UserDao uDao = new UserDao();
					User u = uDao.getUser(username, password);
					JSONRequestHelper.logger.info("User Validated");
					res.getWriter().write(new ObjectMapper().writeValueAsString(u));
				} else {
					JSONRequestHelper.logger.error("Invalid Session blocked");
					res.getWriter().write(new ObjectMapper().writeValueAsString("Invalid Session"));
				}
			} else {
				JSONRequestHelper.logger.error("Invalid Session blocked");
				res.getWriter().write(new ObjectMapper().writeValueAsString("Invalid Session"));
			}
		} else {
			JSONRequestHelper.logger.error("Invalid Session blocked");
			res.getWriter().write(new ObjectMapper().writeValueAsString("Invalid Session"));
		}
	}

}
