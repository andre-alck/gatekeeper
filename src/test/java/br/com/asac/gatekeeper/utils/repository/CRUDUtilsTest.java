package br.com.asac.gatekeeper.utils.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import br.com.asac.gatekeeper.utils.crosscutting.GateKeeperException;

public class CRUDUtilsTest implements Serializable {

	private static final long serialVersionUID = 5690872365954883302L;

	private class XPTO implements Serializable {

		private static final long serialVersionUID = 2523622552010222753L;

		@Id
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

		@Id
		private String bar;
		private byte baz;

		public Foo() {
		}

		public Foo(String bar, byte baz) {
			this.setBar(bar);
			this.setBaz(baz);
		}

		@SuppressWarnings("unused")
		public String getBar() {
			return bar;
		}

		public void setBar(String bar) {
			this.bar = bar;
		}

		@SuppressWarnings("unused")
		public byte getBaz() {
			return baz;
		}

		public void setBaz(byte baz) {
			this.baz = baz;
		}

	}

	private class CRUDUtilsFoo extends CRUDUtils<Foo> implements Serializable {

		private static final long serialVersionUID = -8011793463540652452L;

		public CRUDUtilsFoo() {
			super("foo");
		}

		@Override
		public Foo rowMapper(ResultSet rs) {
			Foo foo = new Foo();
			try {
				foo.setBar(rs.getString("bar"));
				foo.setBaz(rs.getByte("baz"));
			} catch (SQLException e) {
				throw new GateKeeperException(e.getMessage());
			}
			return foo;
		}
	}

	@BeforeEach
	void clear() {
		new CRUDJanitor().truncateAllTables();
	}

	@ParameterizedTest
	@ValueSource(ints = { 1, 3, 5, 10 })
	void givenXInsertions_whenReadingXPTOData_thenListingShouldReturnX(int times) {
		// arrange
		CRUDUtilsXPTO utilsXPTO = new CRUDUtilsXPTO();

		// act
		for (int i = 0; i < times; i++) {
			XPTO xpto = new XPTO(generateRandomPrimaryKey(), -1, (byte) 127, 0.5f);
			utilsXPTO.create(xpto);
		}
		List<XPTO> xptos = (List<XPTO>) utilsXPTO.read();

		// assert
		int expectedSize = times;
		int actualSize = xptos.size();
		assertEquals(expectedSize, actualSize);
	}

	@ParameterizedTest
	@ValueSource(ints = { 1, 3, 5, 10 })
	void givenXInsertions_whenReadingFooData_thenListingShouldReturnX(int times) {
		// arrange
		CRUDUtilsFoo utilsFoo = new CRUDUtilsFoo();

		// act
		for (int i = 0; i < times; i++) {
			Foo foo = new Foo(generateRandomPrimaryKey(), (byte) 127);
			utilsFoo.create(foo);
		}
		List<Foo> foos = (List<Foo>) utilsFoo.read();

		// assert
		int expectedSize = times;
		int actualSize = foos.size();
		assertEquals(expectedSize, actualSize);
	}

	@Test
	void updateFoo() {
		// arrange
		CRUDUtilsFoo utilsFoo = new CRUDUtilsFoo();
		String primaryKey = generateRandomPrimaryKey();

		// act
		Foo foo = new Foo(primaryKey, (byte) -128);
		utilsFoo.create(foo);
		Foo newFoo = new Foo(primaryKey, (byte) 127);
		utilsFoo.update(newFoo);

		// assert
		Foo fooFromDatabase = utilsFoo.find(newFoo);
		assertEquals(newFoo.getBar(), fooFromDatabase.getBar());
		assertEquals(newFoo.getBaz(), fooFromDatabase.getBaz());
	}

	@Test
	void updateXPTO() {
		// arrange
		CRUDUtilsXPTO utilsXPTO = new CRUDUtilsXPTO();
		String primaryKey = generateRandomPrimaryKey();

		// act
		XPTO xpto = new XPTO(primaryKey, -1, (byte) -128, 0.5f);
		utilsXPTO.create(xpto);
		XPTO newXpto = new XPTO(primaryKey, +1, (byte) 127, 1);
		utilsXPTO.update(newXpto);

		// assert
		XPTO xptoFromDatabase = utilsXPTO.find(newXpto);
		assertEquals(newXpto.getX(), xptoFromDatabase.getX());
		assertEquals(newXpto.getP(), xptoFromDatabase.getP());
		assertEquals(newXpto.getT(), xptoFromDatabase.getT());
		assertEquals(newXpto.getO(), xptoFromDatabase.getO());
	}

	private String generateRandomPrimaryKey() {
		char randomLetters[] = new char[10];
		Random random = new Random();

		for (int i = 0; i < 10; i++) {
			randomLetters[i] = (char) (random.nextInt(26) + 'a');
		}

		return String.valueOf(randomLetters);
	}
}
