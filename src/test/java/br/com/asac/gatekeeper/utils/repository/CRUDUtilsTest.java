package br.com.asac.gatekeeper.utils.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import br.com.asac.gatekeeper.utils.crosscutting.GateKeeperException;

public class CRUDUtilsTest implements Serializable {

	private static final long serialVersionUID = 5690872365954883302L;

	private static int amountOfXPTOInsertedRegisters;
	private static int amountOfFooInsertedRegisters;

	private class XPTO implements Serializable {

		private static final long serialVersionUID = 2523622552010222753L;

		private String x;
		private int p;
		private byte t;
		private float o;

		public XPTO() {
		}

		public XPTO(String x, int p, byte t, float o) {
			this.x = x;
			this.p = p;
			this.t = t;
			this.o = o;
		}

		@SuppressWarnings("unused")
		public String getX() {
			return x;
		}

		public void setX(String x) {
			this.x = x;
		}

		@SuppressWarnings("unused")
		public int getP() {
			return p;
		}

		public void setP(int p) {
			this.p = p;
		}

		@SuppressWarnings("unused")
		public byte getT() {
			return t;
		}

		public void setT(byte t) {
			this.t = t;
		}

		@SuppressWarnings("unused")
		public float getO() {
			return o;
		}

		public void setO(float o) {
			this.o = o;
		}

	}

	private class CRUDUtilsXPTO extends CRUDUtils<XPTO> implements Serializable {

		private static final long serialVersionUID = -5009375526557004560L;

		public CRUDUtilsXPTO() {
			super("xpto");
		}

		@Override
		public XPTO rowMapper(ResultSet rs) {
			XPTO xpto = new XPTO();

			try {
				xpto.setX(rs.getString("x"));
				xpto.setP(rs.getInt("p"));
				xpto.setT(rs.getByte("t"));
				xpto.setO(rs.getFloat("o"));
			} catch (SQLException e) {
				throw new GateKeeperException(e.getMessage());
			}

			return xpto;
		}
	}

	private class Foo implements Serializable {

		private static final long serialVersionUID = -2412047218655443890L;

		private String bar;

		public Foo() {
		}

		public Foo(String bar) {
			this.bar = bar;
		}

		@SuppressWarnings("unused")
		public String getBar() {
			return bar;
		}

		public void setXpto(String bar) {
			this.bar = bar;
		}

	}

	// serialize?
	private class CRUDUtilsFoo extends CRUDUtils<Foo> implements Serializable {

		private static final long serialVersionUID = -8011793463540652452L;

		public CRUDUtilsFoo() {
			super("foo");
		}

		@Override
		public Foo rowMapper(ResultSet rs) {
			Foo foo = new Foo();
			try {
				foo.setXpto(rs.getString("bar"));
			} catch (SQLException e) {
				throw new GateKeeperException(e.getMessage());
			}
			return foo;
		}
	}

	@BeforeAll
	static void clear() {
		new CRUDJanitor().truncateAllTables();
	}

	@Test
	void givenEmptyDatabase_whenReadingData_thenListingShouldBeEmpty() {
		// arrange
		CRUDUtilsXPTO utilsXPTO = new CRUDUtilsXPTO();

		// act
		List<XPTO> xptos = (List<XPTO>) utilsXPTO.read();

		// assert
		assertTrue(xptos.isEmpty());
	}

	@ParameterizedTest
	@ValueSource(ints = { 1, 3, 5, 10 })
	void givenXInsertions_whenReadingXPTOData_thenListingShouldReturnX(int times) {
		// arrange
		CRUDUtilsXPTO utilsXPTO = new CRUDUtilsXPTO();
		CRUDUtilsTest.amountOfXPTOInsertedRegisters += times;

		// act
		for (int i = 0; i < times; i++) {
			XPTO xpto = new XPTO("x", -1, (byte) 127, 0.5f);
			utilsXPTO.create(xpto);
		}
		List<XPTO> xptos = (List<XPTO>) utilsXPTO.read();

		// assert
		int expectedSize = amountOfXPTOInsertedRegisters;
		int actualSize = xptos.size();
		assertEquals(expectedSize, actualSize);
	}

	@ParameterizedTest
	@ValueSource(ints = { 1, 3, 5, 10 })
	void givenXInsertions_whenReadingFooData_thenListingShouldReturnX(int times) {
		// arrange
		CRUDUtilsFoo utilsFoo = new CRUDUtilsFoo();
		CRUDUtilsTest.amountOfFooInsertedRegisters += times;

		// act
		for (int i = 0; i < times; i++) {
			Foo foo = new Foo("bar");
			utilsFoo.create(foo);
		}
		List<Foo> foos = (List<Foo>) utilsFoo.read();

		// assert
		int expectedSize = amountOfFooInsertedRegisters;
		int actualSize = foos.size();
		assertEquals(expectedSize, actualSize);
	}

}
