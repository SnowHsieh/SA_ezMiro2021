package ntut.csie.islab.account.user;

import ntut.csie.islab.account.users.entity.User;
import ntut.csie.islab.account.users.repository.UserRepositoryImpl;
import ntut.csie.islab.account.users.usecase.UserRepository;
import ntut.csie.islab.account.users.usecase.register.RegisterInput;
import ntut.csie.islab.account.users.usecase.register.RegisterUseCase;
import ntut.csie.islab.account.users.usecase.register.RegisterUseCaseImpl;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@Transactional
public class RegisterUseCaseTest {
    public DomainEventBus domainEventBus;
    public UserRepository userRepository;
    public String username;
    public String password;
    public String email;

    @BeforeEach
    public void setUp() {
        userRepository = new UserRepositoryImpl();
        domainEventBus = new GoogleEventBus();

        username = UUID.randomUUID().toString();
        password = "123456";
        email = UUID.randomUUID().toString();
    }

    @Test
    public void should_success_when_register_user() {
        RegisterUseCase registerUseCase = new RegisterUseCaseImpl(userRepository, domainEventBus);
        RegisterInput input = registerUseCase.newInput();
        input.setUsername(username);
        input.setPassword(password);
        input.setEmail(email);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        registerUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS, output.getExitCode());

        User user = userRepository.findById(output.getId()).get();

        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(email, user.getEmail());
    }
}
