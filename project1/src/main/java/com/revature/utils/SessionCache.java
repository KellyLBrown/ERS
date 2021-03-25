package com.revature.utils;

import java.util.Optional;

import com.revature.beans.User;

public class SessionCache {

private static Optional<User> loggedInUser;
	
	public static Optional<User> getCurrentUser() {
		return loggedInUser;
	}
	
	public static void setCurrentUser(User u) {
		//BankApplicationDriver.logger.info("User login updated in SessionCache.");
		loggedInUser = Optional.ofNullable(u);
	}
}
