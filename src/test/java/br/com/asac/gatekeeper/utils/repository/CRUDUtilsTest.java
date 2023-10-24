package br.com.asac.gatekeeper.utils.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import br.com.asac.gatekeeper.utils.crosscutting.GateKeeperException;

public class CRUDUtilsTest {

	// serialize?
	class XPTO {
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

		public String getX() {
			return x;
		}

		public void setX(String x) {
			this.x = x;
		}

		public int getP() {
			return p;
		}

		public void setP(int p) {
			this.p = p;
		}

		public byte getT() {
			return t;
		}

		public void setT(byte t) {
			this.t = t;
		}

		public float getO() {
			return o;
		}

		public void setO(float o) {
			this.o = o;
		}

	}

	// serialize?
	class CRUDUtilsXPTO extends CRUDUtils<XPTO> {

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

	// serialize?
	class Foo {
		private String bar;

		public Foo() {
		}

		public Foo(String bar) {
			this.bar = bar;
		}

		public String getBar() {
			return bar;
		}

		public void setXpto(String bar) {
			this.bar = bar;
		}

	}

	// serialize?
	class CRUDUtilsFoo extends CRUDUtils<Foo> {

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

	@Test
	void testInsertXPTO() throws SQLException {
		// arrange
		XPTO xpto = new XPTO("x", -1, (byte) 127, 0.5f);
		CRUDUtilsXPTO utilsXPTO = new CRUDUtilsXPTO();

		// act
		utilsXPTO.create(xpto);

		// assert
	}

	@Test
	void testInsertFoo() throws SQLException {
		// arrange
		Foo foo = new Foo("bar");
		CRUDUtilsFoo utilsFoo = new CRUDUtilsFoo();

		// act
		utilsFoo.create(foo);

		// assert
	}

	@Test
	void testReadFoo() throws SQLException {
		// arrange
		Foo foo = new Foo("bar");
		CRUDUtilsFoo utilsFoo = new CRUDUtilsFoo();

		// act
		utilsFoo.read();

		// assert
	}

}
