package com.revature.services;

import com.revature.beans.User;
import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;
import com.revature.exceptions.InvalidCredentialsException;
import com.revature.exceptions.UsernameAlreadyExistsException;

public class UserServices {

	UserDao userDao;
	ReimbursementDao rDao;
	
	public UserServices(UserDao udao) {
		this.userDao = udao;
	}
	
	/**
	 * Validates the username and password, and return the User object for that user
	 * @throws InvalidCredentialsException if either username is not found or password does not match
	 * @return the User who is now logged in
	 */
	public User login(String username, String password) throws InvalidCredentialsException {
		User u = userDao.getUser(username, password);
		if(u == null) {
			throw new InvalidCredentialsException();
		}
		return u;
	}
	
	/**
	 * Creates the specified User in the persistence layer
	 * @param newUser the User to register
	 * @throws UsernameAlreadyExistsException if the given User's username is taken
	 */
	public void register(User newUser) throws UsernameAlreadyExistsException {
		User u = userDao.getUser(newUser.getUsername(), newUser.getPassword());
		System.out.println(u);
		if(u != null) {
			throw new UsernameAlreadyExistsException();
		} else {
			userDao.addUser(newUser);
		}
		
	}
}
