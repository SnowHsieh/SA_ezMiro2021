package ntut.csie.sslab.account.users.command.usecase.user;

import ntut.csie.sslab.account.AbstractSpringBootJpaTest;
import ntut.csie.sslab.account.users.command.entity.User;
import ntut.csie.sslab.account.users.command.usecase.user.unregister.UnregisterInput;
import ntut.csie.sslab.account.users.command.usecase.user.unregister.UnregisterUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Transactional
public class UnregisterUseCaseTest extends AbstractSpringBootJpaTest {

    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void should_success_when_unregister_user() {
        String userId = registerUser(username, password, email, nickname, role, isThirdParty);

        UnregisterUseCase unregisterUseCase = newUnregisterUseCase();
        UnregisterInput input = unregisterUseCase.newInput();
        input.setUserId(userId);
        input.setPassword(password);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        unregisterUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS, output.getExitCode());

        User user = userRepository.findById(output.getId()).get();

        assertFalse(user.isActivated());
    }


}
