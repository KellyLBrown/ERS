package com.revature.test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.revature.beans.User;
import com.revature.beans.User.UserType;
import com.revature.dao.UserDao;
import com.revature.services.UserServices;

public class UserServicesTest {
	
	@InjectMocks
	public UserServices userSrv;
	
	@Mock
	public UserDao udao;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	
	}
	
	
	

	@Before
	public void setUp() throws Exception {
		udao = new UserDao();
		userSrv = new UserServices(udao);
		
//		User mockUser = new User();
//		mockUser.setUsername("testuser");
//		mockUser.setPassword("testpassword");
//		// DAO layer will only retrieve "testuser" -> simulating only this user existing in persistence layer
//		when(udao.getUser(anyString(), anyString())).then(invocation -> {
//			String uname = invocation.getArgument(0);
//			String pw = invocation.getArgument(1);
//			if (uname.equals("testuser") && pw.equals("testpassword")) {
//				return mockUser;
//			} else {
//				return null;
//			}
//		});
		
		
	}

	@After
	public void tearDown() throws Exception {
		User u = udao.getUser("Steve", "password");
		if(u != null) {
			udao.removeUser(u);
		}
		
	}

	@Test
	public void testLogin() {
		String uname = "kelly";
		String pass = "password";
		User user = userSrv.login(uname, pass);
		assertNotNull(user);
		//verify(udao, times(1)).getUser(uname, pass);
	}

	@Test
	public void testRegister() {
		User u = new User();
		u.setUser_type(UserType.EMPLOYEE);
		u.setUsername("Steve");
		u.setFirst_name("Steve");
		u.setLast_name("Thomson");
		u.setPassword("password");
		u.setEmail("email@email.com");
		userSrv.register(u);
		assertNotNull(udao.getUser("Steve", "password"));
	}

}
