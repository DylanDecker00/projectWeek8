package projectweek8.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import projectweek8.exception.DbException;



public class DbConnection {
	private static final String SCHEMA = "projectsweek8";
	private static final String USER = "projectweek8";
	private static final String PASSWORD = "projectweek8";
	private static final String HOST = "localhost";
	private static final int PORT = 3306;
	
	public static Connection getConnection() {
		String url = String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s&useSSL=false", HOST, PORT, SCHEMA, USER, PASSWORD);
		
		
		try {
			Connection conn = DriverManager.getConnection(url);
			System.out.println("Successfully obtained connection!");
		  return conn;
		} catch (SQLException e) {
			System.out.println("Error getting connection");
		  throw new DbException(e);
		
		}
		
	}
	
	
	
	
	
	
	
}
