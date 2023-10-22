package br.com.asac.gatekeeper.signup.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.asac.gatekeeper.signup.repository.SignUpRepository;

public class SignUpServiceTest {

	private SignUpRepository signUpRepository;

	@BeforeEach
	void clearMocks() {
		this.setSignUpRepository(null);
	}

	@Test
	void givenUnregisteredUser_whenSignUp_thenSignUpAndDoNotShowDialog() {
		// arrange
		this.setSignUpRepository(this.createUnregisteredUserSignUpRepositoryMock());
		SignUpService signUpService = new SignUpService(this.getSignUpRepository());

		// act
		signUpService.signUp(null);

		// assert
		verify(this.getSignUpRepository(), times(1)).signUp(null);
		verify(this.getSignUpRepository(), times(1)).isUserRegistered(null);
	}

	@Test
	void givenUnregisteredUser_whenSignUp_thenShowDialogAndDoNotSignUp() {
		// arrange
		this.setSignUpRepository(this.createRegisteredUserSignUpRepositoryMock());
		SignUpService signUpService = new SignUpService(this.getSignUpRepository());

		// act
		signUpService.signUp(null);

		// assert
		verify(this.getSignUpRepository(), times(0)).signUp(null);
		verify(this.getSignUpRepository(), times(1)).isUserRegistered(null);
	}

	private SignUpRepository createUnregisteredUserSignUpRepositoryMock() {
		return this.createMockedSignUpRepository();
	}

	private SignUpRepository createRegisteredUserSignUpRepositoryMock() {
		SignUpRepository signUpRepository = this.createMockedSignUpRepository();
		when(signUpRepository.isUserRegistered(any())).thenReturn(true);
		return signUpRepository;
	}

	private SignUpRepository createMockedSignUpRepository() {
		return mock(SignUpRepository.class);
	}

	public SignUpRepository getSignUpRepository() {
		return signUpRepository;
	}

	public void setSignUpRepository(SignUpRepository signUpRepository) {
		this.signUpRepository = signUpRepository;
	}
}
