package br.com.asac.gatekeeper.utils.repository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.asac.gatekeeper.utils.crosscutting.GateKeeperException;

public class DatabaseConnectionUtils implements Serializable {

	private static final long serialVersionUID = -2114968805554789890L;

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
			throw new GateKeeperException(e.getMessage());
		}

		return connection;
	}

	private static String createUrl() {
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

	private static String getDatabaseDriver() {
		return DatabaseConnectionUtils.DATABASE_DRIVER;
	}

	private static String getDatabaseName() {
		return DatabaseConnectionUtils.DATABASE_NAME;
	}

	private static String getDatabaseUser() {
		return DatabaseConnectionUtils.DATABASE_USER;
	}

	private static String getDatabasePassword() {
		return DatabaseConnectionUtils.DATABASE_PASSWORD;
	}

}
