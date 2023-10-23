package br.com.asac.gatekeeper.utils.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionUtils {
	private static final String DATABASE_DRIVER = "mysql";
	private static final String DATABASE_NAME = "gatekeeper";
	private static final String DATABASE_USER = "root";
	private static final String DATABASE_PASSWORD = "my-secret-pw";

	public static Connection getConnection() {
		Connection connection = null;

		try {
			String url = createUrl();
			connection = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
			// todo: impl
		}

		return connection;
	}

	private static String createUrl() {
		// "jdbc:mysql://localhost/learning?user=root&password=my-secret-pw"
		StringBuilder url = new StringBuilder();
		url.append("jdbc:");
		url.append(DatabaseConnectionUtils.getDatabaseDriver());
		url.append("://localhost/");
		url.append(DatabaseConnectionUtils.getDatabaseName());
		url.append("?user=");
		url.append(DatabaseConnectionUtils.getDatabaseUser());
		url.append("&password=");
		url.append(DatabaseConnectionUtils.getDatabasePassword());
		return url.toString();
	}

	public static String getDatabaseDriver() {
		return DATABASE_DRIVER;
	}

	public static String getDatabaseName() {
		return DATABASE_NAME;
	}

	public static String getDatabaseUser() {
		return DATABASE_USER;
	}

	public static String getDatabasePassword() {
		return DATABASE_PASSWORD;
	}

}
