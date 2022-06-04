package ntut.csie.islab.account.users.usecase.user.login;

import ntut.csie.sslab.ddd.usecase.Input;

public interface LoginUseCaseInput extends Input {
	String getUsername();

	void setUsername(String username);

	String getPassword();

	void setPassword(String password);
}
