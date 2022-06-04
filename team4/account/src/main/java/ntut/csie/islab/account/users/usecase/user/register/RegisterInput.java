package ntut.csie.islab.account.users.usecase.user.register;

import ntut.csie.sslab.ddd.usecase.Input;

public interface RegisterInput extends Input {
	String getUsername();

	void setUsername(String username);

	String getEmail();

	void setEmail(String email);

	String getPassword();

	void setPassword(String password);
}
