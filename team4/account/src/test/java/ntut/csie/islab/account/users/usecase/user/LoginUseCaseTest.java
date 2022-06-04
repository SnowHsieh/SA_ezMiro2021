package ntut.csie.islab.account.users.usecase.user;

import ntut.csie.islab.account.users.adapter.repository.UserRepositoryImpl;
import ntut.csie.islab.account.users.entity.User;
import ntut.csie.islab.account.users.entity.event.UserLoggedIn;
import ntut.csie.islab.account.users.usecase.UserRepository;
import ntut.csie.islab.account.users.usecase.user.login.LoginUseCase;
import ntut.csie.islab.account.users.usecase.user.login.LoginUseCaseImpl;
import ntut.csie.islab.account.users.usecase.user.login.LoginUseCaseInput;

import ntut.csie.islab.account.users.usecase.user.register.RegisterInput;
import ntut.csie.islab.account.users.usecase.user.register.RegisterUseCase;
import ntut.csie.islab.account.users.usecase.user.register.RegisterUseCaseImpl;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoginUseCaseTest {
    public DomainEventBus domainEventBus;
    public UserRepository userRepository;
    public String username;
    public String password;
    public String email;
    private User user;

    @BeforeEach
    public void setUp() {
        userRepository = new UserRepositoryImpl();
        domainEventBus = new GoogleEventBus();

        username = UUID.randomUUID().toString();
        password = "123456";
        email = UUID.randomUUID().toString();
        user = createRegisteredUser(username, password, email);
    }

    @Test
    public void should_success_when_user_login() {

        LoginUseCase loginUseCase = new LoginUseCaseImpl(userRepository, domainEventBus);
        LoginUseCaseInput input = loginUseCase.newInput();
        input.setUsername(username);
        input.setPassword(password);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        loginUseCase.execute(input, output);
        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS, output.getExitCode());
    }

    private User createRegisteredUser(String username, String password, String email) {
        RegisterUseCase registerUseCase = new RegisterUseCaseImpl(userRepository, domainEventBus);
        RegisterInput input = registerUseCase.newInput();
        input.setUsername(username);
        input.setPassword(password);
        input.setEmail(email);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        registerUseCase.execute(input, output);

        return userRepository.findById(output.getId()).get();
    }
}
