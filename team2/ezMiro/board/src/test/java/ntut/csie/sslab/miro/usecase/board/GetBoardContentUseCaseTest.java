package ntut.csie.sslab.miro.usecase.board;

import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.miro.adapter.presenter.board.getcontent.BoardContentViewModel;
import ntut.csie.sslab.miro.adapter.presenter.board.getcontent.GetBoardContentPresenter;
import ntut.csie.sslab.miro.entity.model.workflow.LaneType;
import ntut.csie.sslab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.miro.usecase.board.getcontent.GetBoardContentInput;
import ntut.csie.sslab.miro.usecase.board.getcontent.GetBoardContentUseCase;
import ntut.csie.sslab.miro.usecase.lane.LaneDto;
import ntut.csie.sslab.miro.usecase.workflow.WorkflowDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GetBoardContentUseCaseTest extends AbstractSpringBootJpaTest {

    @BeforeEach
    public void setUp() {
        super.setUp();
        domainEventBus.register(notifyBoard);
    }

    @Test
    public void should_succeed_when_get_board_content_with_existing_board_id() {

        String boardName = "firstBoard";
        boardId = createBoard(teamId, boardName, userId);

        String firstWorkflowId = createWorkflow(boardId, "firstWorkflow", userId, username);

        String firstStageId = createStage(boardId,
                                        userId,
                                        username,
                                        firstWorkflowId,
                                        "-1",
                                        "firstStage",
                                        -1,
                                        LaneType.Standard.toString());
        String substageId = createStage(boardId,
                                        userId,
                                        username,
                                        firstWorkflowId,
                                        firstStageId,
                                        "substage",
                                        -1,
                                        LaneType.Standard.toString());

        GetBoardContentUseCase useCase = newGetBoardContentUseCase();
        GetBoardContentInput input = (GetBoardContentInput) useCase;
        input.setBoardId(boardId);

        GetBoardContentPresenter presenter = new GetBoardContentPresenter();
        useCase.execute(input, presenter);

        BoardContentViewModel boardContentViewModel = presenter.buildViewModel();

        assertEquals(boardId, boardContentViewModel.getBoardId());
        assertEquals(1, boardContentViewModel.getWorkflows().size());
        assertEquals(1 , boardContentViewModel.getBoardMembers().size());

        WorkflowDto firstWorkflowDto = boardContentViewModel.getWorkflows().get(0);
        assertEquals(firstWorkflowId, firstWorkflowDto.getWorkflowId());
        assertEquals(1, firstWorkflowDto.getLanes().size());
        LaneDto firstStageDto = firstWorkflowDto.getLanes().get(0);
        assertEquals(1, firstStageDto.getLanes().size());
        LaneDto substageDto = firstStageDto.getLanes().get(0);
        assertEquals(firstStageId, firstStageDto.getLaneId());
        assertEquals(0, firstStageDto.getCards().size());
        assertEquals(substageId, substageDto.getLaneId());
        assertEquals(0, substageDto.getCards().size());
        Assertions.assertEquals(userId, boardContentViewModel.getBoardMembers().get(0).getUserId());
    }


    @Test
    public void should_fail_when_get_board_content_with_non_existing_board_id() {

        boardId = UUID.randomUUID().toString();
        GetBoardContentUseCase useCase = newGetBoardContentUseCase();
        GetBoardContentInput input = (GetBoardContentInput) useCase;
        input.setBoardId(boardId);

        GetBoardContentPresenter presenter = new GetBoardContentPresenter();
        useCase.execute(input, presenter);

        BoardContentViewModel boardContentViewModel = presenter.buildViewModel();

        assertEquals(boardId, presenter.getBoardId());
        assertEquals(ExitCode.FAILURE, presenter.getExitCode());
        assertEquals("Get board failed: board not found, board id = " + boardId  , presenter.getMessage());

    }

}
