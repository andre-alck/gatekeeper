package br.com.asac.gatekeeper.signup.service;

import br.com.asac.gatekeeper.signup.controller.User;
import br.com.asac.gatekeeper.signup.repository.SignUpRepository;

public class SignUpService {

	private SignUpRepository signUpRepository;

	public SignUpService() {
		signUpRepository = new SignUpRepository();
	}

	public SignUpService(SignUpRepository signUpRepository) {
		this.signUpRepository = signUpRepository;
	}

	public void signUp(User user) {
		if (!this.isUserRegistered(user)) {
			this.getSignUpRepository().signUp(user);
		}
	}

	public boolean isUserRegistered(User user) {
		return this.getSignUpRepository().isUserRegistered(user);
	}

	public SignUpRepository getSignUpRepository() {
		return signUpRepository;
	}

	public void setSignUpRepository(SignUpRepository signUpRepository) {
		this.signUpRepository = signUpRepository;
	}

}
