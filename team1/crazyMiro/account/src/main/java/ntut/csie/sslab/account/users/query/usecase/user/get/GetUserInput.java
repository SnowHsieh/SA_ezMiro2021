package ntut.csie.sslab.account.users.query.usecase.user.get;

import ntut.csie.sslab.ddd.usecase.Input;

public interface GetUserInput extends Input {

    String getUserId();

    void setUserId(String userId);
}
