package ntut.csie.islab.account.users.usecase.login;

import ntut.csie.sslab.ddd.usecase.cqrs.Command;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;

public interface LoginUseCase extends Command<LoginUseCaseInput, CqrsCommandOutput> {
    String getUsername();

    void setUsername(String username);


    String getPassword();

    void setPassword(String password);
}
