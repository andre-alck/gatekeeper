package br.com.asac.gatekeeper.login.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.asac.gatekeeper.success.SuccessConsts;
import br.com.asac.gatekeeper.user.service.UserService;
import br.com.asac.gatekeeper.utils.controller.UIComponentUtils;

public class LoginMBTest {
	private UserService userService;
	private UIComponentUtils uiComponentUtils;

	@BeforeEach
	void clearMocks() {
		this.setUserService(null);
		this.setUiComponentUtils(null);
	}

	@Test
	void givenUnregisteredUser_whenLogin_thenShowDialogAndDoNotLogin() {
		// arrange
		this.setUserService(this.createUnregisteredUserUserServiceMock());
		this.setUiComponentUtils(this.createMockedUIComponentUtils());
		LoginMB loginMB = new LoginMB(null, this.getUserService(), this.getUiComponentUtils());

		// act
		String successPageRoute = loginMB.login();

		// assert
		assertEquals("", successPageRoute);
		verify(this.getUserService(), times(1)).isUserRegistered(any());
		verify(this.getUiComponentUtils(), times(1)).showDialog(LoginMB.getCouldntIdentifyUserDialogId());
	}

	@Test
	void givenRegisteredUser_whenLogin_thenLoginAndDoNotShowDialog() {
		// arrange
		this.setUserService(this.createRegisteredUserUserServiceMock());
		this.setUiComponentUtils(this.createMockedUIComponentUtils());
		LoginMB loginMB = new LoginMB(null, this.getUserService(), this.getUiComponentUtils());

		/// act
		String successPageRoute = loginMB.login();

		// assert
		assertEquals(SuccessConsts.getSuccessPageRoute(), successPageRoute);
		verify(this.getUserService(), times(1)).isUserRegistered(any());
		verify(this.getUiComponentUtils(), times(0)).showDialog(LoginMB.getCouldntIdentifyUserDialogId());
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
