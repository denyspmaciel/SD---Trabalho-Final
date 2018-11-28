package com.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private final String ip = "localhost";
	private final int port = 5432;
	private final String user = "postgres";
	private final String password = "admin";
	private final String database = "Bd_Times";
	
	public Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:postgresql://"+ip+":"+port+"/"+database, user, password);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}