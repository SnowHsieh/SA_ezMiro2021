package ntut.csie.team5.usecase.board;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.adapter.board.BoardRepositoryImpl;
import ntut.csie.team5.adapter.project.ProjectRepositoryImpl;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.entity.model.project.Project;
import ntut.csie.team5.usecase.board.create.CreateBoardInput;
import ntut.csie.team5.usecase.board.create.CreateBoardUseCase;
import ntut.csie.team5.usecase.board.create.CreateBoardUseCaseImpl;
import ntut.csie.team5.usecase.project.ProjectRepository;
import ntut.csie.team5.usecase.project.create.CreateProjectInput;
import ntut.csie.team5.usecase.project.create.CreateProjectUseCase;
import ntut.csie.team5.usecase.project.create.CreateProjectUseCaseImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateBoardUseCaseTest {

    private ProjectRepository projectRepository;
    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    @Before
    public void setUp() throws Exception {
        projectRepository = new ProjectRepositoryImpl();
        boardRepository = new BoardRepositoryImpl();
        domainEventBus = new DomainEventBus();
    }

    @Test
    public void should_succeed_when_create_board() {

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCaseImpl(boardRepository, domainEventBus);
        CreateBoardInput createBoardInput = createBoardUseCase.newInput();
        CqrsCommandPresenter createBoardOutput = CqrsCommandPresenter.newInstance();

        createBoardInput.setProjectId("1");
        createBoardInput.setBoardName("Board Name");

        createBoardUseCase.execute(createBoardInput, createBoardOutput);

        assertNotNull(createBoardOutput.getId());
        assertEquals(ExitCode.SUCCESS, createBoardOutput.getExitCode());

        Board board = boardRepository.findById(createBoardOutput.getId()).get();
        assertEquals(createBoardOutput.getId(), board.getBoardId());
        assertEquals("1", board.getProjectId());
        assertEquals("Board Name", board.getName());
    }

    @Test
    public void should_succeed_when_create_board_in_project() {
        String projectId = createProject("1", "Project Name");
        Project project = projectRepository.findById(projectId).get();

        String firstBoardId = createBoard(projectId, "Board Name");

        project.commitBoard(firstBoardId);
        projectRepository.save(project);

        project = projectRepository.findById(projectId).get();
        assertEquals(1, project.getCommittedBoards().size());

        String secondBoardId = createBoard(projectId, "Board Name");

        project.commitBoard(secondBoardId);
        projectRepository.save(project);

        project = projectRepository.findById(projectId).get();
        assertEquals(2, project.getCommittedBoards().size());
    }

    private String createProject(String teamId, String projectName) {
        CreateProjectUseCase createProjectUseCase = new CreateProjectUseCaseImpl(projectRepository, domainEventBus);
        CreateProjectInput createProjectInput = createProjectUseCase.newInput();
        CqrsCommandPresenter createProjectOutput = CqrsCommandPresenter.newInstance();

        createProjectInput.setTeamId(teamId);
        createProjectInput.setProjectName(projectName);

        createProjectUseCase.execute(createProjectInput, createProjectOutput);

        return createProjectOutput.getId();
    }

    private String createBoard(String projectId, String boardName) {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCaseImpl(boardRepository, domainEventBus);
        CreateBoardInput createBoardInput = createBoardUseCase.newInput();
        CqrsCommandPresenter createBoardOutput = CqrsCommandPresenter.newInstance();

        createBoardInput.setProjectId(projectId);
        createBoardInput.setBoardName(boardName);

        createBoardUseCase.execute(createBoardInput, createBoardOutput);
        return createBoardOutput.getId();
    }


}
