package ntut.csie.islab.team.usecase;

import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.islab.team.TeamRepository;
import ntut.csie.islab.team.entity.Team;
import ntut.csie.islab.team.repository.TeamRepositoryImpl;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateTeamUseCaseTest extends AbstractSpringBootJpaTest {
    public TeamRepository teamRepository;
    public String username;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        domainEventBus.register(notifyBoardAdapter);
        teamRepository = new TeamRepositoryImpl();
        username = "username";
    }

    @Test
    public void should_success_when_create_team() {
        CreateTeamUseCase createTeamUseCase = new CreateTeamUseCaseImpl(teamRepository, domainEventBus);
        CreateTeamUseCaseInput input = createTeamUseCase.newInput();

        //createBoard > get BoardId
        String userName = "aklsdf";
        input.setAdmin(userName);
        input.setTeamName("TestTeam");

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        createTeamUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS, output.getExitCode());

        Team team = teamRepository.findById(output.getId()).get();
        assertNotNull(team);
        assertEquals("TestTeam", team.getTeamName());
        assertEquals(1, team.getMemberList().size());

        Board board = this.boardRepository.findById(UUID.fromString(team.getBoardId())).get();
        Assertions.assertNotNull(board);
    }

}
