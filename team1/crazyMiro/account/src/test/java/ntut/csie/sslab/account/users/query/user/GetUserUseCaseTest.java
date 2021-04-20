package ntut.csie.sslab.account.users.query.user;

import ntut.csie.sslab.account.users.query.adapter.presenter.get.GetUserPresenter;
import ntut.csie.sslab.account.users.query.usecase.UserDto;
import ntut.csie.sslab.account.users.query.usecase.user.get.GetUserUseCase;
import ntut.csie.sslab.account.AbstractSpringBootJpaTest;
import ntut.csie.sslab.account.users.query.usecase.user.get.GetUserInput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


//@Transactional don't add this annotation!
public class GetUserUseCaseTest extends AbstractSpringBootJpaTest {

    String userId;

    @BeforeEach
    public void setUp() {
        super.setUp();
        username += UUID.randomUUID().toString();
        email += UUID.randomUUID().toString();
        userId = registerUser(username, password, email, nickname, role, isThirdParty);
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteById(userId);
    }

    @Test
    public void should_success_when_get_user() {
        GetUserUseCase useCase = newGetUserUseCase();

        GetUserInput input = useCase.newInput();
        input.setUserId(userId);

        GetUserPresenter presenter = new GetUserPresenter();

        useCase.execute(input, presenter);

        assertEquals(ExitCode.SUCCESS, presenter.getExitCode());

        UserDto user = presenter.buildViewModel().getUser();
        assertEquals(userId, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(nickname, user.getNickname());
        assertEquals(role, user.getRole());
    }

    @Test
    public void should_return_message_when_get_user_fail() {
        String userId = "failId";
        GetUserUseCase useCase = newGetUserUseCase();
        GetUserInput input = useCase.newInput();
        input.setUserId(userId);

        GetUserPresenter presenter = new GetUserPresenter();

        useCase.execute(input, presenter);

        assertEquals(ExitCode.FAILURE, presenter.getExitCode());
        assertEquals("Get user failed: user not found, user id = " + userId, presenter.buildViewModel().getMessage());
    }
}
