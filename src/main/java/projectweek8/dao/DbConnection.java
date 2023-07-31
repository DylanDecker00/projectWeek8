package projectweek8.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import projectweek8.exception.DbException;

public class DbConnection {
	 public static final String HOST = "localhost";
	    private static final String PASSWORD = "projectsweek8";
	    private static final int PORT = 3306;
	    private static final String SCHEMA = "projectsweek8";
	    private static final String USER = "projectsweek8";

	    public static Connection getConnection() throws DbException {
	        String uri = "jdbc:mysql://" + HOST + ":" + PORT + "/" + SCHEMA;
	        try {
	            Connection connection = DriverManager.getConnection(uri, USER, PASSWORD);
	            System.out.println("Connection to the database successful!");
	            return connection;
	        } catch (SQLException e) {
	            System.err.println("Error connecting to the database.");
	            throw new DbException("Database connection error.", e);
	        }
	    }
	}
