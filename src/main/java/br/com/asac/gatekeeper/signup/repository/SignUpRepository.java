package br.com.asac.gatekeeper.signup.repository;

import java.io.Serializable;

import br.com.asac.gatekeeper.user.model.User;
import br.com.asac.gatekeeper.user.repository.UserRepository;

public class SignUpRepository implements Serializable {

	private static final long serialVersionUID = 8038812745104363424L;

	private UserRepository userRepository;

	public SignUpRepository() {
		this.setUserRepository(new UserRepository());
	}

	public void signUp(User user) {
		this.getUserRepository().create(user);
	}

	private UserRepository getUserRepository() {
		return userRepository;
	}

	private void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

}
