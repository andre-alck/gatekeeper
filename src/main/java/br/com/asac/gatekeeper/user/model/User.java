package br.com.asac.gatekeeper.user.model;

import java.io.Serializable;

import br.com.asac.gatekeeper.utils.repository.Id;

public class User implements Serializable {

	private static final long serialVersionUID = 5838614084149816599L;

	@Id
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
