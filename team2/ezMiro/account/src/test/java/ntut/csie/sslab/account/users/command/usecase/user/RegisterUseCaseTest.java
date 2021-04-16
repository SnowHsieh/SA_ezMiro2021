package ntut.csie.sslab.account.users.command.usecase.user;

import ntut.csie.sslab.account.users.command.entity.Role;
import ntut.csie.sslab.account.users.command.usecase.user.register.RegisterInput;
import ntut.csie.sslab.account.users.command.usecase.user.register.RegisterUseCase;
import ntut.csie.sslab.account.AbstractSpringBootJpaTest;

import ntut.csie.sslab.account.users.command.entity.User;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.Assert.*;
@Transactional
public class RegisterUseCaseTest extends AbstractSpringBootJpaTest {

    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void should_success_when_register_user() {
        RegisterUseCase registerUseCase = newRegisterUseCase();
        RegisterInput input = registerUseCase.newInput();
        input.setUsername(username);
        input.setPassword(password);
        input.setEmail(email);
        input.setNickname(nickname);
        input.setRole(role);
        input.setThirdParty(false);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        registerUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS, output.getExitCode());

        User user = userRepository.findById(output.getId()).get();

        assertEquals(username, user.getUsername());
        assertTrue(encrypt.checkPassword(password, user.getPassword()));
        assertEquals(email, user.getEmail());
        assertEquals(nickname, user.getNickname());
        assertEquals(role, user.getRole().toString());
    }

    @Test
    public void should_success_when_register_user_using_third_party() {
        RegisterUseCase registerUseCase = newRegisterUseCase();
        RegisterInput input = registerUseCase.newInput();
        input.setUsername(username);
        input.setPassword(password);
        input.setEmail(email);
        input.setNickname(nickname);
        input.setRole(role);
        input.setThirdParty(true);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        registerUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS, output.getExitCode());

        User user = userRepository.findById(output.getId()).get();

        assertEquals(username, user.getUsername());
        assertTrue(encrypt.checkPassword(password, user.getPassword()));
        assertEquals(email, user.getEmail());
        assertEquals(nickname, user.getNickname());
        assertEquals(role, user.getRole().toString());
    }

    @Test
    public void should_success_when_register_user_using_third_party_but_user_already_register() {
        should_success_when_register_user_using_third_party();
        RegisterUseCase registerUseCase = newRegisterUseCase();
        RegisterInput input = registerUseCase.newInput();
        input.setUsername(username);
        input.setPassword(password);
        input.setEmail(email);
        input.setNickname(nickname);
        input.setRole(role);
        input.setThirdParty(true);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        registerUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS, output.getExitCode());

        User user = userRepository.findById(output.getId()).get();

        assertEquals(username, user.getUsername());
        assertTrue(encrypt.checkPassword(password, user.getPassword()));
        assertEquals(email, user.getEmail());
        assertEquals(nickname, user.getNickname());
        assertEquals(role, user.getRole().toString());
    }

    @Test
    public void should_success_when_register_user_using_third_party_but_user_username_or_email_exist() {
        should_success_when_register_user_using_third_party();
        String fakeEmail = "fake@gmail.com";
        RegisterUseCase registerUseCase = newRegisterUseCase();
        RegisterInput input = registerUseCase.newInput();
        input.setUsername(username);
        input.setPassword(password);
        input.setEmail(fakeEmail);
        input.setNickname(nickname);
        input.setRole(role);
        input.setThirdParty(true);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        registerUseCase.execute(input, output);

        assertNull(output.getId());
        assertEquals(ExitCode.FAILURE, output.getExitCode());
        assertEquals("Third party registration failed, internal error: username or email already exists", output.getMessage());
    }

    @Test
    public void should_fail_when_register_user_with_same_name() {
        String newEmail = "123@mail.com";
        registerUser(username, password, email, nickname, role, isThirdParty);
        RegisterUseCase registerUseCase = newRegisterUseCase();
        RegisterInput input = registerUseCase.newInput();
        input.setUsername(username);
        input.setPassword(password);
        input.setEmail(newEmail);
        input.setNickname(nickname);
        input.setRole(role);
        input.setThirdParty(false);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        registerUseCase.execute(input, output);

        assertEquals(ExitCode.FAILURE, output.getExitCode());
        assertEquals("Username:" + username + " already exists.", output.getMessage());
    }

    @Test
    public void should_fail_when_register_user_with_same_email() {
        String newUsername = "123";
        registerUser(username, password, email, nickname, role, isThirdParty);
        RegisterUseCase registerUseCase = newRegisterUseCase();
        RegisterInput input = registerUseCase.newInput();
        input.setUsername(newUsername);
        input.setPassword(password);
        input.setEmail(email);
        input.setNickname(nickname);
        input.setRole(role);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        registerUseCase.execute(input, output);

        assertEquals(ExitCode.FAILURE, output.getExitCode());
        assertEquals("Email:" + email + " already exists.", output.getMessage());
    }
}
