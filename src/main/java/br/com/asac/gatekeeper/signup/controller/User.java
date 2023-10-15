package br.com.asac.gatekeeper.signup.controller;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 6498345365150256780L;
	
	private String name;
	private String password;
	private boolean registered;

	public User() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRegistered() {
		return registered;
	}

	public void setRegistered(boolean registered) {
		this.registered = registered;
	}
}
