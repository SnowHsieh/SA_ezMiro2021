package ntut.csie.sslab.account.users.command.usecase.user.register;

import ntut.csie.sslab.ddd.usecase.Input;

public interface RegisterInput extends Input {
	String getUsername();

	void setUsername(String username);

	String getEmail();

	void setEmail(String email);

	String getPassword();

	void setPassword(String password);

	String getNickname();

	void setNickname(String nickname);

	String getRole();

	void setRole(String role);

	boolean isThirdParty();

	void setThirdParty(boolean b);
}
