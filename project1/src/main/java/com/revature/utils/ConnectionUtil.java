package com.revature.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

	
	/**
	 * Singleton utility for creating and retrieving database connection
	 */
	public class ConnectionUtil {
		private static ConnectionUtil cu = null;
		private static Properties prop;
		
		/**
		 * This method should read in the "database.properties" file and load
		 * the values into the Properties variable
		 */
		private ConnectionUtil() {
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream is = classLoader.getResourceAsStream("database.properties");
			prop = new Properties();
			try {
				prop.load(is);
				//BankApplicationDriver.logger.info("Connection initialized");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//BankApplicationDriver.logger.error("Connection failed!");
				e.printStackTrace();
			}
			
		}
		
		public static synchronized ConnectionUtil getConnectionUtil() {
			if(cu == null)
				cu = new ConnectionUtil();
			
			return cu;
		}
		
		/**
		 * This method should create and return a Connection object
		 * @return a Connection to the database
		 */
		public Connection getConnection() {
			// Hint: use the Properties variable to setup your Connection object
			try {
				Connection conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("usr"),
						prop.getProperty("pswd"));
				return conn;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			return null;
		}
	}


