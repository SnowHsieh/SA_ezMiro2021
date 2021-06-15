package ntut.csie.team5.usecase.board;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.entity.model.project.Project;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.board.create.CreateBoardInput;
import ntut.csie.team5.usecase.board.create.CreateBoardUseCase;
import ntut.csie.team5.usecase.board.create.CreateBoardUseCaseImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateBoardUseCaseTest extends AbstractTest {

    @Test
    public void should_succeed_when_create_board() {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCaseImpl(boardRepository, domainEventBus);
        CreateBoardInput createBoardInput = createBoardUseCase.newInput();
        CqrsCommandPresenter createBoardOutput = CqrsCommandPresenter.newInstance();

        createBoardInput.setProjectId(projectId);
        createBoardInput.setBoardName(boardName);

        createBoardUseCase.execute(createBoardInput, createBoardOutput);

        assertNotNull(createBoardOutput.getId());
        assertEquals(ExitCode.SUCCESS, createBoardOutput.getExitCode());

        Board board = boardRepository.findById(createBoardOutput.getId()).orElse(null);
        assertNotNull(board);
        assertEquals(createBoardOutput.getId(), board.getBoardId());
        assertEquals(projectId, board.getProjectId());
        assertEquals(boardName, board.getName());
    }

    @Test
    public void should_succeed_when_create_board_in_project() {
        String projectId = createProject(teamId, projectName);
        Project project = projectRepository.findById(projectId).get();

        String firstBoardId = createBoard(projectId, boardName);

        project.commitBoard(firstBoardId);
        projectRepository.save(project);

        project = projectRepository.findById(projectId).get();
        assertEquals(1, project.getCommittedBoards().size());

        String secondBoardId = createBoard(projectId, boardName);

        project.commitBoard(secondBoardId);
        projectRepository.save(project);

        project = projectRepository.findById(projectId).get();
        assertEquals(2, project.getCommittedBoards().size());
    }


}
