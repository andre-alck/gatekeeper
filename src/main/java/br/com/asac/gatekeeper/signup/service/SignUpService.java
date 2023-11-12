package br.com.asac.gatekeeper.signup.service;

import java.io.Serializable;

import br.com.asac.gatekeeper.signup.repository.SignUpRepository;
import br.com.asac.gatekeeper.user.model.User;
import br.com.asac.gatekeeper.user.service.UserService;

public class SignUpService implements Serializable {

	private static final long serialVersionUID = 4238282803098411106L;

	private UserService userService;
	private SignUpRepository signUpRepository;

	public SignUpService() {
		this.setUserService(new UserService());
		this.setSignUpRepository(new SignUpRepository());
	}

	public SignUpService(UserService userService, SignUpRepository signUpRepository) {
		this.setUserService(userService);
		this.setSignUpRepository(signUpRepository);
	}

	public void signUp(User user) {
		if (!this.getUserService().isUserNameAlreadyTaken(user)) {
			this.getSignUpRepository().signUp(user);
		}
	}

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public SignUpRepository getSignUpRepository() {
		return this.signUpRepository;
	}

	public void setSignUpRepository(SignUpRepository signUpRepository) {
		this.signUpRepository = signUpRepository;
	}

}
