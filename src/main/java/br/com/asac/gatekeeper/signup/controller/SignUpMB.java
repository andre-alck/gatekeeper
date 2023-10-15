package br.com.asac.gatekeeper.signup.controller;

import java.io.Serializable;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class SignUpMB implements Serializable {

	private static final long serialVersionUID = -587850118181924287L;

	private User user = new User();

	public void signUp() {
		if (true) {
			user.setRegistered(true);

			// todo: implement signUp method.
			throw new UnsupportedOperationException();
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
