package br.com.asac.gatekeeper.signup.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.asac.gatekeeper.signup.repository.SignUpRepository;
import br.com.asac.gatekeeper.user.service.UserService;

public class SignUpServiceTest {

	private UserService userService;
	private SignUpRepository signUpRepository;

	@BeforeEach
	void clearAllMocks() {
		this.setUserService(null);
		this.setSignUpRepository(null);
	}

	@Test
	void givenUnregisteredUser_whenSignUp_thenSignUp() {
		// arrange
		this.setUserService(this.createUnregisteredUserUserServiceMock());
		this.setSignUpRepository(this.createMockedSignUpRepositoryMock());
		SignUpService signUpService = new SignUpService(this.getUserService(), this.getSignUpRepository());

		// act
		signUpService.signUp(null);

		// assert
		verify(this.getUserService(), times(1)).isUserNameAlreadyTaken(any());
		verify(this.getSignUpRepository(), times(1)).signUp(any());
	}

	@Test
	void givenRegisteredUser_whenSignUp_thenSignUp() {
		// arrange
		this.setUserService(this.createRegisteredUserUserServiceMock());
		this.setSignUpRepository(this.createMockedSignUpRepositoryMock());
		SignUpService signUpService = new SignUpService(this.getUserService(), this.getSignUpRepository());

		// act
		signUpService.signUp(null);

		// assert
		verify(this.getUserService(), times(1)).isUserNameAlreadyTaken(any());
		verify(this.getSignUpRepository(), times(0)).signUp(any());
	}

	private UserService createUnregisteredUserUserServiceMock() {
		UserService userService = this.createMockedUserService();
		return userService;
	}

	private UserService createRegisteredUserUserServiceMock() {
		UserService userService = this.createMockedUserService();
		when(userService.isUserNameAlreadyTaken(any())).thenReturn(true);
		return userService;
	}

	private UserService createMockedUserService() {
		UserService userService = mock(UserService.class);
		assertTrue(Mockito.mockingDetails(userService).isMock());
		return userService;
	}

	private SignUpRepository createMockedSignUpRepositoryMock() {
		SignUpRepository signUpRepository = mock(SignUpRepository.class);
		assertTrue(Mockito.mockingDetails(signUpRepository).isMock());
		return signUpRepository;
	}

	private UserService getUserService() {
		return this.userService;
	}

	private void setUserService(UserService userService) {
		this.userService = userService;
	}

	private SignUpRepository getSignUpRepository() {
		return this.signUpRepository;
	}

	private void setSignUpRepository(SignUpRepository signUpRepository) {
		this.signUpRepository = signUpRepository;
	}

}
