package com.revature.test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.revature.beans.User;
import com.revature.dao.UserDao;
import com.revature.services.UserServices;

public class ProjectTestSuite {
	
	@InjectMocks
	public UserServices userSrv;
	
	@Mock
	public UserDao udao;
	
	@Before
	public void setupMocks() {
		User mockUser = new User();
		mockUser.setUsername("testuser");
		mockUser.setPassword("testpassword");
		// DAO layer will only retrieve "testuser" -> simulating only this user existing in persistence layer
		when(udao.getUser(anyString(), anyString())).then(invocation -> {
			String uname = invocation.getArgument(0);
			String pw = invocation.getArgument(1);
			if (uname.equals("testuser") && pw.equals("testpassword")) {
				return mockUser;
			} else {
				return null;
			}
		});
	}
	
	
	@Test
	public void testLoginWithValidCredentials() {
		String username = "testuser";
		String pw = "testpassword";
		User user = userSrv.login(username, pw);
		assertNotNull(user);
		verify(udao, times(1)).getUser(username, pw);
	}
	
	
	
	
	
	
	
	
	

}
