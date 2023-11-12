package br.com.asac.gatekeeper.signup.controller;

import java.io.Serializable;

import br.com.asac.gatekeeper.signup.service.SignUpService;
import br.com.asac.gatekeeper.success.SuccessConsts;
import br.com.asac.gatekeeper.user.model.User;
import br.com.asac.gatekeeper.user.service.UserService;
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
	private UserService userService;
	private UIComponentUtils uiComponentUtils;
	private static final String USERNAME_ALREADY_TOKEN_DIALOG_MESSAGE = "username-already-taken";

	public SignUpMB() {
		this.setUser(new User());
		this.setSignUpService(new SignUpService());
		this.setUserService(new UserService());
		this.setUiComponentUtils(new UiComponentUtilsPrimeFaces());
	}

	public SignUpMB(User user, SignUpService signUpService, UserService userService,
			UIComponentUtils uiComponentUtils) {
		this.setUser(user);
		this.setSignUpService(signUpService);
		this.setUserService(userService);
		this.setUiComponentUtils(uiComponentUtils);
	}

	public String signUp() {
		if (this.getUserService().isUserRegistered(this.getUser())) {
			this.getUiComponentUtils().showDialog(SignUpMB.USERNAME_ALREADY_TOKEN_DIALOG_MESSAGE);
			return "";
		}

		this.getSignUpService().signUp(this.getUser());
		return SuccessConsts.getSuccessPageRoute();
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SignUpService getSignUpService() {
		return this.signUpService;
	}

	public void setSignUpService(SignUpService signUpService) {
		this.signUpService = signUpService;
	}

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UIComponentUtils getUiComponentUtils() {
		return this.uiComponentUtils;
	}

	public void setUiComponentUtils(UIComponentUtils uiComponentUtils) {
		this.uiComponentUtils = uiComponentUtils;
	}

	public static String getUsernameAlreadyTokenDialogMessage() {
		return SignUpMB.USERNAME_ALREADY_TOKEN_DIALOG_MESSAGE;
	}

}
