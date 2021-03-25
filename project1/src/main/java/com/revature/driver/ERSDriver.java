package com.revature.driver;

import com.revature.beans.Reimbursement;
import com.revature.beans.User;
import com.revature.beans.Reimbursement.ReimbursementType;
import com.revature.beans.User.UserType;
import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;

public class ERSDriver {
	
	public static void main(String[] args) {
		UserDao udao = new UserDao();
		ReimbursementDao rdao = new ReimbursementDao();
		
		User u = new User();
		u.setEmail("email@email.com");
		u.setFirst_name("Kelly");
		u.setLast_name("BrownIE");
		u.setPassword("passwords");
		u.setUser_id(0);
		u.setUsername("kellybrown");
		u.setUser_type(UserType.FINANCEMANAGER);
		//udao.addUser(u);
		
		System.out.println(udao.getAllUsers());
		System.out.println(udao.getUser(1));
		// userDao is fully functional
		Reimbursement r = new Reimbursement();
		r.setId(1);
		r.setAmount(500.34);
		r.setDescription("I got some coffee for the whole office");
		r.setAuthorId(u.getUser_id());
		r.setType(ReimbursementType.FOOD);
		
		//rdao.addTicket(r);
		System.out.println(rdao.getPending());
		
		// reimbursementdao is fully functional to the best of my knowledge
		
		
		// that means the Daos are done and now its time for services and then connecting front end to back end
		
		
		
		
		
	}

}
