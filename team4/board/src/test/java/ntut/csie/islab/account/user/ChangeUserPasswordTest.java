package ntut.csie.islab.account.user;

import ntut.csie.islab.account.users.entity.User;
import ntut.csie.islab.account.users.repository.UserRepositoryImpl;
import ntut.csie.islab.account.users.usecase.UserRepository;
import ntut.csie.islab.account.users.usecase.changePassword.ChangeUserPasswordUseCase;
import ntut.csie.islab.account.users.usecase.changePassword.ChangeUserPasswordUseCaseImpl;
import ntut.csie.islab.account.users.usecase.changePassword.ChangeUserPasswordUseCaseInput;
import ntut.csie.islab.account.users.usecase.register.RegisterInput;
import ntut.csie.islab.account.users.usecase.register.RegisterUseCase;
import ntut.csie.islab.account.users.usecase.register.RegisterUseCaseImpl;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ChangeUserPasswordTest {
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
    public void should_success_when_change_user_password() {
        ChangeUserPasswordUseCase changeUserPasswordUseCase = new ChangeUserPasswordUseCaseImpl(userRepository, domainEventBus);
        ChangeUserPasswordUseCaseInput input = changeUserPasswordUseCase.newInput();
        String newPassword = "jjhlenf";
        input.setUsername(username);
        input.setPassword(newPassword);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        changeUserPasswordUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS, output.getExitCode());

        User user = userRepository.findById(output.getId()).get();

        assertEquals(username, user.getUsername());
        assertEquals(newPassword, user.getPassword());
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
