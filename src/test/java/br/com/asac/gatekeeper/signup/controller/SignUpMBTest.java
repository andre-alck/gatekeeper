//package br.com.asac.gatekeeper.signup.controller;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import br.com.asac.gatekeeper.signup.service.SignUpService;
//import br.com.asac.gatekeeper.utils.controller.UIComponentUtils;
//
//public class SignUpMBTest {
//
//	private SignUpService signUpService;
//	private UIComponentUtils uiComponentUtils;
//
//	@BeforeEach
//	void clearMocks() {
//		this.setSignUpService(null);
//		this.setUiComponentUtils(null);
//	}
//
//	@Test
//	void givenUnregisteredUser_whenSignUp_thenSignUpAndDoNotShowDialog() {
//		// arrange
//		this.setSignUpService(this.createUnregisteredUserSignUpServiceMock());
//		this.setUiComponentUtils(this.createMockedUIComponentUtils());
//		SignUpMB signUpMB = new SignUpMB(null, this.getSignUpService(), this.getUiComponentUtils());
//
//		// act
//		signUpMB.signUp();
//
//		// assert
//		verify(this.getSignUpService(), times(1)).signUp(any());
//		verify(this.getUiComponentUtils(), times(0)).showDialog(SignUpMB.USERNAME_ALREADY_TOKEN_DIALOG_MESSAGE);
//	}
//
//	@Test
//	void givenRegisteredUser_whenSignUp_thenShowDialogAndDoNotSignUp() {
//		// arrange
//		this.setUiComponentUtils(this.createMockedUIComponentUtils());
//		this.setSignUpService(this.createRegisteredUserSignUpServiceMock());
//		SignUpMB signUpMB = new SignUpMB(null, this.getSignUpService(), this.getUiComponentUtils());
//
//		// act
//		signUpMB.signUp();
//
//		// assert
//		verify(this.getSignUpService(), times(0)).signUp(any());
//		verify(this.getUiComponentUtils(), times(1)).showDialog(SignUpMB.USERNAME_ALREADY_TOKEN_DIALOG_MESSAGE);
//	}
//
//	private SignUpService createUnregisteredUserSignUpServiceMock() {
//		return this.createMockedSignUpService();
//	}
//
//	private SignUpService createRegisteredUserSignUpServiceMock() {
//		SignUpService signUpService = this.createMockedSignUpService();
//		when(signUpService.isUserRegistered(any())).thenReturn(true);
//		return signUpService;
//	}
//
//	private SignUpService createMockedSignUpService() {
//		return mock(SignUpService.class);
//	}
//
//	private UIComponentUtils createMockedUIComponentUtils() {
//		return mock(UIComponentUtils.class);
//	}
//
//	public SignUpService getSignUpService() {
//		return signUpService;
//	}
//
//	public void setSignUpService(SignUpService signUpService) {
//		this.signUpService = signUpService;
//	}
//
//	public UIComponentUtils getUiComponentUtils() {
//		return uiComponentUtils;
//	}
//
//	public void setUiComponentUtils(UIComponentUtils uiComponentUtils) {
//		this.uiComponentUtils = uiComponentUtils;
//	}
//
//}
