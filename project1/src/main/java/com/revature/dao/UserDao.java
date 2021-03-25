package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PSQLException;

import com.revature.beans.User;
import com.revature.beans.User.UserType;
import com.revature.utils.ConnectionUtil;


// handling the data for the users
public class UserDao {
	
	ConnectionUtil connUtil = ConnectionUtil.getConnectionUtil();
	Connection conn = connUtil.getConnection();
	
		/**
		 * Inserts a new user into the persistence layer
		 * @param user the user to insert
		 * @return the newly added user object
		 */
		public User addUser(User user) {
			try {
				int type_id;
				if(user.getUser_type().equals(UserType.FINANCEMANAGER)) {
					type_id = 2; 
				} else {
					type_id = 1;
				}
				String sql = "INSERT into users VALUES(default, ?, ?, ? ,? ,?, ?);";
				PreparedStatement reg = conn.prepareStatement(sql);
				reg.setString(1, user.getUsername());
				reg.setString(2, user.getPassword());
				reg.setString(3, user.getFirst_name());
				reg.setString(4, user.getLast_name());
				reg.setString(5, user.getEmail());
				reg.setInt(6, type_id);
				reg.execute();
				return user;
			} catch (PSQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		/**
		 * Retrieves a user by id
		 * @param userId the id to search by
		 * @return the user object
		 */
		public User getUser(Integer userId) {
			try {
				String sql = "SELECT * FROM users WHERE user_id = '" + userId + "';";
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				if(rs != null) {
					rs.next();
					User u = new User();
					u.setUser_id(rs.getInt(1));
					u.setUsername(rs.getString(2));
					u.setPassword(rs.getString(3));
					u.setFirst_name(rs.getString(4));
					u.setLast_name(rs.getString(5));
					u.setEmail(rs.getString(6));
					if(rs.getInt(7) == 2) {
						u.setUser_type(UserType.FINANCEMANAGER);
					} else {
						u.setUser_type(UserType.EMPLOYEE);
					}
					//BankApplicationDriver.logger.info("User grabbed by ID");
					return u;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//BankApplicationDriver.logger.warn("User grab by ID failed");
				e.printStackTrace();
			}
			return null;
		}
		
		/**
		 * Retrieves a user by username and password
		 * @param username
		 * @param pass
		 * @return the user object
		 */
		public User getUser(String username, String pass) {
			try {
				String sql = "SELECT * FROM users WHERE username = '" + username + "' and password = '" + pass + "';";
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				if(rs.next()) {
					User u = new User();
					u.setUser_id(rs.getInt(1));
					u.setUsername(rs.getString(2));
					u.setPassword(rs.getString(3));
					u.setFirst_name(rs.getString(4));
					u.setLast_name(rs.getString(5));
					u.setEmail(rs.getString(6));
					if(rs.getInt(7) == 2) {
						u.setUser_type(UserType.FINANCEMANAGER);
					} else {
						u.setUser_type(UserType.EMPLOYEE);
					}
					return u;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		/**
		 * Retrieves all users in the persistence layer
		 * @return a list of all users
		 */
		public List<User> getAllUsers() {
			List<User> uList = new ArrayList<User>();
			try {
			String sql = "SELECT * FROM users";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				User u = new User();
				u.setUser_id(rs.getInt(1));
				u.setUsername(rs.getString(2));
				u.setPassword(rs.getString(3));
				u.setFirst_name(rs.getString(4));
				u.setLast_name(rs.getString(5));
				u.setEmail(rs.getString(6));
				if(rs.getInt(7) == 2) {
					u.setUser_type(UserType.FINANCEMANAGER);
				} else {
					u.setUser_type(UserType.EMPLOYEE);
				}
				uList.add(u);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//BankApplicationDriver.logger.warn("User list grab failed");
			e.printStackTrace();
		}
			//BankApplicationDriver.logger.info("Complete User List grabbed");
			return uList;
		}
		
		/**
		 * Updates a specific user
		 * @param u the user to update
		 * @return the newly updated user object
		 */
		public User updateUser(User u) {
			//updateType(u);
			try {
				String sql = "UPDATE users SET password = '" + u.getPassword() + "', firstname = '" + 
			u.getFirst_name() + "', lastname = '" + u.getLast_name() + "', email = '" + u.getEmail() + 
						"' WHERE user_id = " + u.getUser_id() +  ";";
				Statement s = conn.createStatement();
				System.out.println(sql);
				s.executeUpdate(sql);
				//BankApplicationDriver.logger.info("User updated");
				return u;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//BankApplicationDriver.logger.warn("User update failed");
				e.printStackTrace();
			}
			return null;
		}
		
		/**
		 * Deletes a user from the persistence layer
		 * @param u the user to remove
		 * @return true if successful; false if not
		 */
		public boolean removeUser(User u) {
			//removeType(u.getUser_id());
			try {
				String sql = "DELETE FROM users WHERE user_id = " + u.getUser_id() + ";";
				System.out.println(sql);
				Statement s = conn.createStatement();
				s.executeUpdate(sql);
				//BankApplicationDriver.logger.info("User removed");
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//BankApplicationDriver.logger.warn("User removal failed");
				e.printStackTrace();
			}
			return false;
		}
	}
		
		// ----------------------- Type table helpers -----------------------------------------
		
		
//		private void addType(User u) {
//			try {
//				String sql = "INSERT INTO roles VALUES("+ u.getUser_id() + ", '" + u.getUser_type() +  "');";
//				Statement s = conn.createStatement();
//				s.executeUpdate(sql);
//				//BankApplicationDriver.logger.info("User updated");
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				//BankApplicationDriver.logger.warn("User update failed");
//				e.printStackTrace();
//			}
//		}
		
//		private String getType(int id) {
//			try {
//				String sql = "SELECT * FROM roles WHERE user_id = '" + id + "';";
//				Statement s = conn.createStatement();
//				ResultSet rs = s.executeQuery(sql);
//				if(rs.next()) {
//					//rs.next();
//					System.out.println("Getting Type");
//					String a = rs.getString(2);
//					System.out.println(a);
//					return a;
//					//BankApplicationDriver.logger.info("User grabbed by ID");
//				}
//				
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				//BankApplicationDriver.logger.warn("User grab by ID failed");
//				e.printStackTrace();
//			}
//			System.out.println("Not getting Type");
//			return null;
//		}
//		
//		
//		private void updateType(User u) {
//			try {
//				String sql = "UPDATE roles SET user_role = '" + u.getUser_type() +
//						"' WHERE user_id = " + u.getUser_id() +  ";";
//				Statement s = conn.createStatement();
//				System.out.println(sql);
//				s.executeUpdate(sql);
//				//BankApplicationDriver.logger.info("User updated");
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				//BankApplicationDriver.logger.warn("User update failed");
//				e.printStackTrace();
//			}
//		}
//		
//		
//		private void removeType(int id) {
//			try {
//				String sql = "DELETE FROM roles WHERE user_id = " + id + ";";
//				System.out.println(sql);
//				Statement s = conn.createStatement();
//				s.executeUpdate(sql);
//				//BankApplicationDriver.logger.info("User removed");
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				//BankApplicationDriver.logger.warn("User removal failed");
//				e.printStackTrace();
//			}
//		}
//	}


