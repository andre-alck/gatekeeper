package br.com.asac.gatekeeper.signup.controller;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 5838614084149816599L;

	private String name;
	private String password;

	public User() {
	}
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
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
}
