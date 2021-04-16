package ntut.csie.sslab.account.users.command.usecase.user.register;

import ntut.csie.sslab.ddd.usecase.Output;

public interface RegisterOutput extends Output {
	String getUserId();
	
	void setUserId(String userId);
}
