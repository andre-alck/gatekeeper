package br.com.asac.gatekeeper.utils.repository;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.com.asac.gatekeeper.utils.crosscutting.GateKeeperException;

public abstract class CRUDUtils<T> implements CRUDOperations<T>, Serializable {

	private static final long serialVersionUID = -3600165865852401916L;

	private Connection connection;
	private String table;

	public CRUDUtils(String table) {
		this.initConnection();
		this.setTable(table);
	}

	@Override
	public void create(T t) {
		try {
			this.getConnection().createStatement().executeUpdate(this.insertGenericQueryForT(t));
		} catch (Exception e) {
			throw new GateKeeperException(e.getMessage());
		}
	}

	private String insertGenericQueryForT(T t) {
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO ");
		query.append(this.getTable());
		query.append("(");

		List<Field> fieldsFromGenericClass = this.retrieveValidFieldsFromGenericClass(t);
		fieldsFromGenericClass.forEach(field -> {
			query.append(field.getName());
			query.append(", ");
		});

		this.deleteExtraCommaAndSpace(query);
		query.append(") VALUES (");

		fieldsFromGenericClass.forEach(field -> {
			String getter = this.generateGetterMethodSignature(field.getName());
			String value = null;

			try {
				value = String.valueOf(t.getClass().getMethod(getter).invoke(t));
			} catch (Exception e) {
				throw new GateKeeperException(e.getMessage());
			}

			if (field.getType().getName().equals("java.lang.String")) {
				query.append("\"");
				query.append(value);
				query.append("\"");
			} else {
				query.append(value);
			}

			query.append(", ");
		});

		this.deleteExtraCommaAndSpace(query);
		query.append(")");
		return query.toString();
	}

	private List<Field> retrieveValidFieldsFromGenericClass(T t) {
		List<Field> allFields = Arrays.asList(t.getClass().getDeclaredFields());
		return allFields.stream().filter(tElement -> this.isFieldValid(tElement)).toList();
	}

	private boolean isFieldValid(Field field) {
		return !(field.getName().contains("this") || field.getName().contains("serialVersionUID"));
	}

	private void deleteExtraCommaAndSpace(StringBuilder query) {
		query.deleteCharAt(query.length() - 1);
		query.deleteCharAt(query.length() - 1);
	}

	private String generateGetterMethodSignature(String name) {
		String firstLetterOfFieldNameCapitalized = name.substring(0, 1).toUpperCase();
		String remainderOfFieldName = name.substring(1, name.length());
		String classAttributeCapitalized = firstLetterOfFieldNameCapitalized + remainderOfFieldName;
		return "get" + classAttributeCapitalized;
	}

	@Override
	public Collection<T> read() {
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM ");
		query.append(this.getTable());

		Collection<T> ts = new ArrayList<>();
		ResultSet rs = null;
		try {
			rs = this.getConnection().createStatement().executeQuery(query.toString());

			while (rs.next()) {
				T t = this.rowMapper(rs);
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
		Map<String, Object> map = this.retrieveMapBetweenFieldNameAndItsValue(t);
		String key = map.keySet().iterator().next();
		Object value = map.get(key);

		StringBuilder query = new StringBuilder();
		query.append("UPDATE ");
		query.append(this.getTable());
		query.append(" SET ");

		T tfromDatabase = this.find(t);
		this.generateUpdateBasedOnFieldsOfGenericClass(t, tfromDatabase, query);

		query.append(" WHERE ");

		query.append(key);
		query.append(" = \"");

		query.append(value);
		query.append("\"");

		try {
			this.getConnection().createStatement().executeUpdate(query.toString());
		} catch (Exception e) {
			throw new GateKeeperException(e.getMessage());
		}
	}

	private Map<String, Object> retrieveMapBetweenFieldNameAndItsValue(T t) {
		String nameOfIdField = this.retrieveNameOfIdFieldFromGenericClass(t);
		Object valueOfIdField = this.retrieveValueOfIdField(t, nameOfIdField);

		Map<String, Object> idAndValue = new HashMap<>();
		idAndValue.put(nameOfIdField, valueOfIdField);

		return idAndValue;
	}

	private String retrieveNameOfIdFieldFromGenericClass(T t) {
		Optional<Field> optionalOfIdField = this.retrieveOptionalOfIdField(t);
		return optionalOfIdField.get().getName();
	}

	private Optional<Field> retrieveOptionalOfIdField(T t) {
		Optional<Field> optionalOfIdField = this.retrieveValidFieldsFromGenericClass(t).stream()
				.filter(tElement -> tElement.isAnnotationPresent(Id.class)).findFirst();

		if (optionalOfIdField.isEmpty()) {
			StringBuilder exception = new StringBuilder();
			exception.append("No fields with the ");
			exception.append(Id.class.getName());
			exception.append(" annotation were found in the with ");
			exception.append(t.getClass().getName());
			exception.append(" class.");
			throw new GateKeeperException(exception.toString());
		}

		return optionalOfIdField;
	}

	private Object retrieveValueOfIdField(T t, String nameOfIdField) {
		String getter = this.generateGetterMethodSignature(nameOfIdField);
		Object value = null;
		try {
			value = (Object) t.getClass().getMethod(getter).invoke(t);
		} catch (Exception e) {
			throw new GateKeeperException(e.getMessage());
		}

		return value;
	}

	private void generateUpdateBasedOnFieldsOfGenericClass(T tNew, T tfromDatabase, StringBuilder query) {
		List<Field> validFieldsFromGenericClass = this.retrieveValidFieldsFromGenericClassForUpdate(tNew);
		validFieldsFromGenericClass.forEach(field -> {
			String getter = this.generateGetterMethodSignature(field.getName());
			String valueNew;
			String valueFromDatabase;

			try {
				valueNew = String.valueOf(tNew.getClass().getMethod(getter).invoke(tNew));
				valueFromDatabase = String.valueOf(tfromDatabase.getClass().getMethod(getter).invoke(tfromDatabase));

				if (!valueNew.equals(valueFromDatabase)) {
					query.append(field.getName());
					query.append(" = ");
					if (field.getType().getName().equals("java.lang.String")) {
						query.append("\"");
						query.append(valueNew);
						query.append("\"");
						query.append(", ");
					} else {
						query.append(valueNew);
						query.append(", ");
					}

				}
			} catch (Exception e) {
				throw new GateKeeperException(e.getMessage());
			}
		});
		this.deleteExtraCommaAndSpace(query);
	}

	private List<Field> retrieveValidFieldsFromGenericClassForUpdate(T t) {
		List<Field> validFields = new ArrayList<>(this.retrieveValidFieldsFromGenericClass(t));
		validFields.removeIf(f -> f.isAnnotationPresent(Id.class));
		return validFields;
	}

	public T find(T t) {
		Map<String, Object> map = this.retrieveMapBetweenFieldNameAndItsValue(t);
		String key = map.keySet().iterator().next();
		Object value = map.get(key);

		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM ");
		query.append(this.getTable());
		query.append(" WHERE ");

		query.append(key);
		query.append(" = \"");

		query.append(value);
		query.append("\"");

		T tFromDatabase = null;
		ResultSet rs = null;
		try {
			rs = this.getConnection().createStatement().executeQuery(query.toString());

			while (rs.next()) {
				tFromDatabase = this.rowMapper(rs);
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

		return tFromDatabase;
	}

	@Override
	public void delete(T t) {
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM ");
		query.append(this.getTable());
		query.append(" WHERE ");

		Map<String, Object> map = this.retrieveMapBetweenFieldNameAndItsValue(t);
		String key = map.keySet().iterator().next();
		Object value = map.get(key);

		query.append(key);
		query.append(" = ");

		if (value instanceof String) {
			query.append("\"");
			query.append(value);
			query.append("\"");
		} else {
			query.append(value);
		}

		try {
			this.getConnection().createStatement().executeUpdate(query.toString());
		} catch (SQLException e) {
			throw new GateKeeperException(e.toString());
		}
	}

	public abstract T rowMapper(ResultSet rs);

	private Connection getConnection() {
		return this.connection;
	}

	private void initConnection() {
		if (this.getConnection() == null) {
			this.connection = DatabaseConnectionUtils.getConnection();
		}
	}

	private String getTable() {
		return this.table;
	}

	private void setTable(String table) {
		if (table != null) {
			this.table = table;
		} else {
			throw new GateKeeperException("Table cannot be null.");
		}
	}
}
