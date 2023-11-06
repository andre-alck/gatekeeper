package br.com.asac.gatekeeper.user.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

import br.com.asac.gatekeeper.user.model.User;
import br.com.asac.gatekeeper.user.repository.UserRepository;
import br.com.asac.gatekeeper.utils.repository.CRUDOperations;

public class UserService implements CRUDOperations<User>, Serializable {

	private static final long serialVersionUID = -4722851828108442005L;

	private UserRepository userRepository;

	public UserService() {
		this.setUserRepository(new UserRepository());
	}

	public boolean isUserRegistered(User user) {
		User userFromDatabase = this.getUserRepository().find(user);
		return userFromDatabase != null;
	}

	@Override
	public void create(User t) {
		this.getUserRepository().create(t);
	}

	@Override
	public Collection<User> read() {
		return this.getUserRepository().read();
	}

	@Override
	public void update(User t) {
		this.getUserRepository().update(t);
	}

	@Override
	public void delete(User t) {
		this.getUserRepository().delete(t);
	}

	public UserRepository getUserRepository() {
		return this.userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

}
