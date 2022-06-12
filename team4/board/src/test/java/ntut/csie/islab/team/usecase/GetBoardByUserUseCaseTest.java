package ntut.csie.islab.team.usecase;

import ntut.csie.islab.account.users.entity.User;
import ntut.csie.islab.account.users.repository.UserRepositoryImpl;
import ntut.csie.islab.account.users.usecase.UserRepository;
import ntut.csie.islab.account.users.usecase.register.RegisterInput;
import ntut.csie.islab.account.users.usecase.register.RegisterUseCase;
import ntut.csie.islab.account.users.usecase.register.RegisterUseCaseImpl;
import ntut.csie.islab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.islab.team.TeamRepository;
import ntut.csie.islab.team.entity.Team;
import ntut.csie.islab.team.repository.TeamRepositoryImpl;
import ntut.csie.islab.team.usecase.createteam.CreateTeamUseCase;
import ntut.csie.islab.team.usecase.createteam.CreateTeamUseCaseImpl;
import ntut.csie.islab.team.usecase.createteam.CreateTeamUseCaseInput;
import ntut.csie.islab.team.usecase.getboard.GetBoardListByUserUseCase;
import ntut.csie.islab.team.usecase.getboard.GetBoardListByUserUseCaseImpl;
import ntut.csie.islab.team.usecase.getboard.GetBoardListByUserUseCaseInput;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetBoardByUserUseCaseTest extends AbstractSpringBootJpaTest {
    public TeamRepository teamRepository;
    public UserRepository userRepository;

    public String username;
    public String password;
    public String email;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        domainEventBus.register(notifyBoardAdapter);
        teamRepository = new TeamRepositoryImpl();
        userRepository = new UserRepositoryImpl();
        username = UUID.randomUUID().toString();
        password = "123456";
        email = UUID.randomUUID().toString();
    }
    @Test
    public void should_success_when_get_board_by_user() {

        // create user
        User user = this.register_user();
        // use user to create team >get board id
        Team team1 = this.create_team(user.getUsername(),"TestTeam0612_1");
        Team team2 = this.create_team(user.getUsername(),"TestTeam0612_2");


        // get board by usecase
        GetBoardListByUserUseCase getBoardListByUserUsecase = new GetBoardListByUserUseCaseImpl(teamRepository,boardRepository, domainEventBus);

        GetBoardListByUserUseCaseInput input = getBoardListByUserUsecase.newInput();
        input.setUserName(user.getUsername());
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        getBoardListByUserUsecase.execute(input,output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS, output.getExitCode());
//        System.out.println(output.getMessage());
        assertEquals(2,output.getMessage().split(",").length);

    }
    public Team create_team(String userName,String teamName) {
        CreateTeamUseCase createTeamUseCase = new CreateTeamUseCaseImpl(teamRepository, domainEventBus);
        CreateTeamUseCaseInput input = createTeamUseCase.newInput();

        //createBoard > get BoardId
        input.setAdmin(userName);
        input.setTeamName(teamName);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        createTeamUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS, output.getExitCode());

        Team team = teamRepository.findById(output.getId()).get();
        return team;
    }


    private User register_user() {
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

        return user;
    }

}
