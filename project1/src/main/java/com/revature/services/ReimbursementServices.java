package com.revature.services;

import com.revature.beans.Reimbursement;
import com.revature.beans.User;
import com.revature.beans.User.UserType;
import com.revature.dao.ReimbursementDao;

public class ReimbursementServices {
	
	public ReimbursementDao rdao;
	
	public ReimbursementServices(ReimbursementDao dao) {
		this.rdao = dao;
	}
	
	// creates a new ticket in the database
	public Reimbursement createNewTicket(Reimbursement r) {
		Reimbursement nr = rdao.addTicket(r);
		if(nr != null) {
			return r;
		}
		return null;
	}
	
	// updates a ticket in the database if the user is a financial manager
	public boolean updateTicket(User u, Reimbursement r) {
		if(u.getUser_type().equals(UserType.FINANCEMANAGER)) {
			Reimbursement nr = rdao.updateTicket(r);
			if(nr != null) {
				return true;
			}
		}
		return false;
		
	}

}
