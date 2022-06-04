package ntut.csie.islab.team.usecase;

import ntut.csie.islab.account.users.repository.UserRepositoryImpl;
import ntut.csie.islab.account.users.usecase.UserRepository;
import ntut.csie.islab.team.TeamRepository;
import ntut.csie.islab.team.entity.Team;
import ntut.csie.islab.team.repository.TeamRepositoryImpl;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateTeamUsecaseTest {
    public DomainEventBus domainEventBus;
    public TeamRepository teamRepository;
    public String username;
    public String boardId;
    @BeforeEach
    public void setUp() {
        teamRepository = new TeamRepositoryImpl();
        domainEventBus = new GoogleEventBus();
        username = UUID.randomUUID().toString();
        boardId = UUID.randomUUID().toString();
    }
    @Test
    public void should_success_when_create_team() {
        CreateTeamUseCase createTeamUseCase = new CreateTeamUseCaseImpl(teamRepository, domainEventBus);
        CreateTeamUseCaseInput input = createTeamUseCase.newInput();

        //createBoard > get BoardId
        String userName = "akl;sdf";
        input.setAdmin(userName);
        input.setBoarId(boardId);
        input.setTeamName("TestTeam");

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        createTeamUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS, output.getExitCode());

        Team team = teamRepository.findById(output.getId()).get();
        assertNotNull(team);
        assertEquals("TestTeam", team.getTeamName());
        assertEquals(1,team.getMemberList().size());
        assertEquals(boardId,team.getBoardId());

    }

}
