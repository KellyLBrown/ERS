package com.revature.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.revature.beans.Reimbursement;
import com.revature.beans.User;
import com.revature.beans.Reimbursement.ReimbursementType;
import com.revature.beans.Reimbursement.StatusType;
import com.revature.beans.User.UserType;
import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;
import com.revature.services.ReimbursementServices;
import com.revature.services.UserServices;

public class ReimbursementServicesTest {
	
	@InjectMocks
	public ReimbursementServices rserv;
	
	@InjectMocks
	public UserServices userv;
	
	@Mock
	public UserDao udao;
	
	
	@Mock
	public ReimbursementDao rdao;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		udao = new UserDao();
		userv = new UserServices(udao);
		rdao = new ReimbursementDao();
		rserv = new ReimbursementServices(rdao);
	}

	@After
	public void tearDown() throws Exception {
	
	}


	@Test
	public void testCreateNewTicket() {
		Reimbursement r = new Reimbursement();
		User u = udao.getUser("kelly", "password");
		r.setAmount(143.55);
		r.setAuthorId(u.getUser_id());
		r.setDescription("Whatever");
		r.setStatus(StatusType.PENDING);
		r.setType(ReimbursementType.OTHER);
		rserv.createNewTicket(r);
		//verify(rdao, times(1)).addTicket(r);
	}

	@Test
	public void testUpdateTicket() {
		User u = udao.getUser("manager", "password");
		Reimbursement r = rdao.getTicket(rdao.getAllTickets().size());
		rserv.updateTicket(u, r);
		
	}

}
