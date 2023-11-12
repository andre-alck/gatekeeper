package br.com.asac.gatekeeper.login.controller;

import java.io.Serializable;

import br.com.asac.gatekeeper.success.SuccessConsts;
import br.com.asac.gatekeeper.user.model.User;
import br.com.asac.gatekeeper.user.service.UserService;
import br.com.asac.gatekeeper.utils.controller.UIComponentUtils;
import br.com.asac.gatekeeper.utils.controller.primefaces.UiComponentUtilsPrimeFaces;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class LoginMB implements Serializable {

	private static final long serialVersionUID = -5915798641142750981L;

	private User user;
	private UserService userService;
	private UIComponentUtils uiComponentUtils;
	private static final String COULDNT_IDENTIFY_USER_DIALOG_ID = "couldnt-identify-user";

	public LoginMB() {
		this.setUser(new User());
		this.setUserService(new UserService());
		this.setUiComponentUtils(new UiComponentUtilsPrimeFaces());
	}

	public LoginMB(User user, UserService userService, UIComponentUtils uiComponentUtils) {
		this.setUser(user);
		this.setUserService(userService);
		this.setUiComponentUtils(uiComponentUtils);
	}

	public String login() {
		if (this.getUserService().isUserRegistered(this.getUser())) {
			return SuccessConsts.getSuccessPageRoute();
		}

		this.getUiComponentUtils().showDialog(LoginMB.COULDNT_IDENTIFY_USER_DIALOG_ID);
		return "";
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public static String getCouldntIdentifyUserDialogId() {
		return LoginMB.COULDNT_IDENTIFY_USER_DIALOG_ID;
	}
}
