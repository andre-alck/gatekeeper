package br.com.asac.gatekeeper.signup.service;

import java.io.Serializable;

import br.com.asac.gatekeeper.signup.repository.SignUpRepository;
import br.com.asac.gatekeeper.user.model.User;
import br.com.asac.gatekeeper.user.service.UserService;

public class SignUpService implements Serializable {

	private static final long serialVersionUID = 4238282803098411106L;

	private SignUpRepository signUpRepository;
	private UserService userService;

	public SignUpService() {
		this.setSignUpRepository(new SignUpRepository());
		this.setUserService(new UserService());
	}

	public SignUpService(SignUpRepository signUpRepository) {
		this.signUpRepository = signUpRepository;
	}

	public void signUp(User user) {
		if (!this.getUserService().isUserRegistered(user)) {
			this.getSignUpRepository().signUp(user);
		}
	}

	public SignUpRepository getSignUpRepository() {
		return signUpRepository;
	}

	public void setSignUpRepository(SignUpRepository signUpRepository) {
		this.signUpRepository = signUpRepository;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
