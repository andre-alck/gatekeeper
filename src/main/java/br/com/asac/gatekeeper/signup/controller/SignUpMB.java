package br.com.asac.gatekeeper.signup.controller;

import java.io.Serializable;

import org.primefaces.PrimeFaces;

import br.com.asac.gatekeeper.signup.service.SignUpService;
import br.com.asac.gatekeeper.utils.controller.UIComponentUtils;
import br.com.asac.gatekeeper.utils.controller.primefaces.UiComponentUtilsPrimeFaces;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class SignUpMB implements Serializable {

	private static final long serialVersionUID = -587850118181924287L;

	private User user;
	private SignUpService signUpService;
	private UIComponentUtils uiComponentUtils;
	public static String USERNAME_ALREADY_TOKEN_DIALOG_MESSAGE = "username-already-taken";

	public SignUpMB() {
		user = new User();
		signUpService = new SignUpService();
		uiComponentUtils = new UiComponentUtilsPrimeFaces();
	}

	public SignUpMB(User user, SignUpService signUpService, UIComponentUtils uiComponentUtils) {
		this.user = user;
		this.signUpService = signUpService;
		this.uiComponentUtils = uiComponentUtils;
	}

	public void signUp() {
		if (this.getSignUpService().isUserRegistered(this.getUser())) {
			this.getUiComponentUtils().showDialog((USERNAME_ALREADY_TOKEN_DIALOG_MESSAGE));
		} else {
			this.getSignUpService().signUp(this.getUser());
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SignUpService getSignUpService() {
		return signUpService;
	}

	public void setSignUpService(SignUpService signUpService) {
		this.signUpService = signUpService;
	}

	public UIComponentUtils getUiComponentUtils() {
		return uiComponentUtils;
	}

	public void setUiComponentUtils(UIComponentUtils uiComponentUtils) {
		this.uiComponentUtils = uiComponentUtils;
	}
}
