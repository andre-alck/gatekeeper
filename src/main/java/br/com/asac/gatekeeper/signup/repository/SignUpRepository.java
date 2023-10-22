package br.com.asac.gatekeeper.signup.repository;

import br.com.asac.gatekeeper.signup.controller.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignUpRepository {

	public void signUp(User user) {
	}

	public boolean isUserRegistered(User user) {
		return true;
	}

}
