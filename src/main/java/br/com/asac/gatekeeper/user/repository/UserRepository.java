package br.com.asac.gatekeeper.user.repository;

import java.io.Serializable;
import java.sql.ResultSet;

import br.com.asac.gatekeeper.user.model.User;
import br.com.asac.gatekeeper.utils.crosscutting.GateKeeperException;
import br.com.asac.gatekeeper.utils.repository.CRUDUtils;

public class UserRepository extends CRUDUtils<User> implements Serializable {

	private static final long serialVersionUID = 2806154311778196181L;

	public UserRepository() {
		super("user");
	}

	public boolean isUserRegistered(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User rowMapper(ResultSet rs) {
		User user = new User();
		try {
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
		} catch (Exception e) {
			throw new GateKeeperException(e.getMessage());
		}

		return user;
	}

}
