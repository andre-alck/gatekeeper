package br.com.asac.gatekeeper.utils.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.asac.gatekeeper.utils.crosscutting.GateKeeperException;

public class CRUDJanitor {

	private Connection connection;

	public CRUDJanitor() {
		this.initConnection();
	}

	public void truncateAllTables() {
		try {
			ResultSet rs = this.getConnection().createStatement().executeQuery(
					"SELECT CONCAT('TRUNCATE TABLE ', TABLE_NAME) query FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = \"gatekeeper\"");

			while (rs.next()) {
				this.getConnection().createStatement().executeUpdate(rs.getString("query"));
			}
		} catch (SQLException e) {
			throw new GateKeeperException(e.getMessage());
		}
	}

	private Connection getConnection() {
		return connection;
	}

	private void initConnection() {
		this.connection = DatabaseConnectionUtils.getConnection();
	}
}
