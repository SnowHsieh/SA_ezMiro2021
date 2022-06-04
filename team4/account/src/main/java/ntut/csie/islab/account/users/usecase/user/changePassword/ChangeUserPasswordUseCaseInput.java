package ntut.csie.islab.account.users.usecase.user.changePassword;

import ntut.csie.sslab.ddd.usecase.Input;

public interface ChangeUserPasswordUseCaseInput extends Input {
    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);
}
