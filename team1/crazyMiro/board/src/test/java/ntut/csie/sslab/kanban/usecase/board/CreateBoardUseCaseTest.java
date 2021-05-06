package ntut.csie.sslab.kanban.usecase.board;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.board.Board;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.board.create.CreateBoardInput;
import ntut.csie.sslab.kanban.usecase.board.create.CreateBoardUseCase;
import ntut.csie.sslab.kanban.usecase.board.create.CreateBoardUseCaseImpl;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateBoardUseCaseTest extends AbstractSpringBootJpaTest {
    @Test
    void create_a_board(){
        String boardId = UUID.randomUUID().toString();
        String boardName = "BoardName";
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCaseImpl(boardRepository, domainEventBus);
        CreateBoardInput input = createBoardUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setBoardName(boardName);

        createBoardUseCase.execute(input, output);

        assertEquals(boardId, output.getId());
        assertEquals(ExitCode.SUCCESS, output.getExitCode());
        assertTrue(boardRepository.findById(input.getBoardId()).isPresent());
        Board board = boardRepository.findById(input.getBoardId()).get();
        assertEquals(boardId, board.getBoardId());
        assertEquals(boardName, board.getBoardName());
        assertEquals(0, board.getCommittedFigures().size());
        assertEquals(1, eventListener.getEventCount());
    }
}
