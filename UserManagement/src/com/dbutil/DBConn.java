package com.dbutil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Jaanvi.S.C.H IT19801100
 * DB connection
 *
 */
public class DBConn {

	private static String url = "jdbc:mysql://localhost:3306/gbDB";
	private static String username = "root";
	private static String password = "";

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

}
