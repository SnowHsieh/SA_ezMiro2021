package ntut.csie.sslab.account.users.command.usecase.user.unregister;

import ntut.csie.sslab.ddd.usecase.Input;

public interface UnregisterInput extends Input {
	String getUserId();

	void setUserId(String userId);

	String getPassword();

	void setPassword(String password);

}
