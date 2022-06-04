package ntut.csie.islab.account.users.usecase.user.changePassword;

import ntut.csie.islab.account.users.usecase.user.login.LoginUseCaseInput;
import ntut.csie.sslab.ddd.usecase.cqrs.Command;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;

public interface ChangeUserPasswordUseCase extends Command<ChangeUserPasswordUseCaseInput, CqrsCommandOutput> {
    String getUsername();

    void setUsername(String username);


    String getPassword();

    void setPassword(String password);
}
