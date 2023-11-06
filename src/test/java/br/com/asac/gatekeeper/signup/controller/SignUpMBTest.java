package br.com.asac.gatekeeper.signup.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.asac.gatekeeper.signup.service.SignUpService;
import br.com.asac.gatekeeper.user.service.UserService;
import br.com.asac.gatekeeper.utils.controller.UIComponentUtils;

public class SignUpMBTest {

	private SignUpService signUpService;
	private UserService userService;
	private UIComponentUtils uiComponentUtils;

	@BeforeEach
	void clearMocks() {
		this.setSignUpService(null);
		this.setUserService(null);
		this.setUiComponentUtils(null);
	}

	@Test
	void givenUnregisteredUser_whenSignUp_thenSignUpAndDoNotShowDialog() {
		// arrange
		this.setSignUpService(this.createMockedSignUpService());
		this.setUserService(this.createUnregisteredUserUserServiceMock());
		this.setUiComponentUtils(this.createMockedUIComponentUtils());
		SignUpMB signUpMB = new SignUpMB(null, this.getSignUpService(), this.getUserService(),
				this.getUiComponentUtils());

		// act
		signUpMB.signUp();

		// assert
		verify(this.getUserService(), times(1)).isUserRegistered(any());
		verify(this.getUiComponentUtils(), times(0)).showDialog(SignUpMB.getUsernameAlreadyTokenDialogMessage());
		verify(this.getSignUpService(), times(1)).signUp(any());
	}

	@Test
	void givenRegisteredUser_whenSignUp_thenShowDialogAndDoNotSignUp() {
		// arrange
		this.setSignUpService(this.createMockedSignUpService());
		this.setUserService(this.createRegisteredUserUserServiceMock());
		this.setUiComponentUtils(this.createMockedUIComponentUtils());
		SignUpMB signUpMB = new SignUpMB(null, this.getSignUpService(), this.getUserService(),
				this.getUiComponentUtils());

		// act
		signUpMB.signUp();

		// assert
		verify(this.getUserService(), times(1)).isUserRegistered(any());
		verify(this.getUiComponentUtils(), times(1)).showDialog(SignUpMB.getUsernameAlreadyTokenDialogMessage());
		verify(this.getSignUpService(), times(0)).signUp(any());
	}

	private SignUpService createMockedSignUpService() {
		return mock(SignUpService.class);
	}

	private UserService createUnregisteredUserUserServiceMock() {
		return this.createMockedUserService();
	}

	private UserService createRegisteredUserUserServiceMock() {
		UserService userService = this.createMockedUserService();
		when(userService.isUserRegistered(any())).thenReturn(true);
		return userService;
	}

	private UserService createMockedUserService() {
		UserService userService = mock(UserService.class);
		assertTrue(Mockito.mockingDetails(userService).isMock());
		return userService;
	}

	private UIComponentUtils createMockedUIComponentUtils() {
		UIComponentUtils uiComponentUtils = mock(UIComponentUtils.class);
		assertTrue(Mockito.mockingDetails(uiComponentUtils).isMock());
		return uiComponentUtils;
	}

	/* getters n setters */

	private SignUpService getSignUpService() {
		return signUpService;
	}

	private void setSignUpService(SignUpService signUpService) {
		this.signUpService = signUpService;
	}

	private UserService getUserService() {
		return userService;
	}

	private void setUserService(UserService userService) {
		this.userService = userService;
	}

	private UIComponentUtils getUiComponentUtils() {
		return uiComponentUtils;
	}

	private void setUiComponentUtils(UIComponentUtils uiComponentUtils) {
		this.uiComponentUtils = uiComponentUtils;
	}

}
