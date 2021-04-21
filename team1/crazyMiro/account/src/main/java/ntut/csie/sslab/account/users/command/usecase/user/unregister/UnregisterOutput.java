package ntut.csie.sslab.account.users.command.usecase.user.unregister;

import ntut.csie.sslab.ddd.usecase.Output;

public interface UnregisterOutput extends Output {
	String getUserId();
	
	void setUserId(String userId);
}
