package br.com.asac.gatekeeper.utils.repository;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.asac.gatekeeper.utils.crosscutting.GateKeeperException;

public abstract class CRUDUtils<T> implements CRUDOperations<T>, Serializable {

	private static final long serialVersionUID = -3600165865852401916L;

	private Connection connection;
	private String table;

	public CRUDUtils(String table) {
		connection = DatabaseConnectionUtils.getConnection();
		this.table = table;
	}

	@Override
	public void create(T t) {
		try {
			this.getConnection().createStatement().executeQuery(insertGenericQueryForT(t));
		} catch (Exception e) {
			throw new GateKeeperException(e.getMessage());
		}

	}

	private String insertGenericQueryForT(T t) {
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO ");
		query.append(this.getTable());
		query.append("(");

		Field[] fieldsFromGenericClass = t.getClass().getDeclaredFields();

		for (int i = 0; i < fieldsFromGenericClass.length; i++) {
			if (!isFieldAReferenceToTheClassItself(fieldsFromGenericClass[i])) {
				query.append(fieldsFromGenericClass[i].getName());

				if (i == fieldsFromGenericClass.length - 2) {
					break;
				} else {
					query.append(", ");
				}
			}
		}
		query.append(") VALUES (");

		for (int i = 0; i < fieldsFromGenericClass.length; i++) {
			if (!isFieldAReferenceToTheClassItself(fieldsFromGenericClass[i])) {
				String classAttributeCapitalized = fieldsFromGenericClass[i].getName().substring(0, 1).toUpperCase()
						+ fieldsFromGenericClass[i].getName().substring(1,
								fieldsFromGenericClass[i].getName().length());
				String getter = "get" + classAttributeCapitalized;
				String value = null;
				try {
					value = String.valueOf(t.getClass().getMethod(getter).invoke(t));
				} catch (Exception e) {
					throw new GateKeeperException(e.getMessage());
				}
				if (fieldsFromGenericClass[i].getType().getName().equals("java.lang.String")) {
					query.append("\"");
					query.append(value);
					query.append("\"");
				} else {
					query.append(value);
				}

				if (i == fieldsFromGenericClass.length - 2) {
					break;
				} else {
					query.append(", ");
				}
			}
		}
		query.append(")");
		return query.toString();
	}

	private boolean isFieldAReferenceToTheClassItself(Field field) {
		return field.getName().contains("this");
	}

	@Override
	public Collection<T> read() {
		String query = "SELECT * FROM " + this.getTable();

		Collection<T> ts = new ArrayList<>();
		ResultSet rs = null;
		try {
			rs = connection.createStatement().executeQuery(query);

			while (rs.next()) {
				T t = rowMapper(rs);
				ts.add(t);
			}
		} catch (SQLException e) {
			throw new GateKeeperException(e.getMessage());
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new GateKeeperException(e.getMessage());
			}
		}

		return ts;
	}

	@Override
	public void update(T t) {
	}

	@Override
	public void delete(T t) {
	}

	public abstract T rowMapper(ResultSet rs);

	public Connection getConnection() {
		return connection;
	}

	public String getTable() {
		return table;
	}
}
