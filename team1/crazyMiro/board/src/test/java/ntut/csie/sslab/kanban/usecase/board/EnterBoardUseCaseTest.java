package ntut.csie.sslab.kanban.usecase.board;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.kanban.entity.model.board.Board;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.board.enter.EnterBoardInput;
import ntut.csie.sslab.kanban.usecase.board.enter.EnterBoardUseCase;
import ntut.csie.sslab.kanban.usecase.board.enter.EnterBoardUseCaseImpl;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnterBoardUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void user_enter_board() {
        String boardId = UUID.randomUUID().toString();
        createBoard(boardId, "Jay board");
        eventListener.clearEventCount();
        String userId = "user1";
        EnterBoardUseCase enterBoardUseCase = new EnterBoardUseCaseImpl(boardRepository, domainEventBus);
        EnterBoardInput input = enterBoardUseCase.newInput();
        CqrsCommandOutput output = CqrsCommandPresenter.newInstance();
        input.setUserId(userId);
        input.setBoardId(boardId);

        enterBoardUseCase.execute(input, output);

        Board board = boardRepository.findById(boardId).get();
        assertEquals(output.getId(), board.getBoardSessions().get(0).getBoardSessionId());
        assertEquals(1, board.getBoardSessions().size());
        assertEquals(userId, board.getBoardSessions().get(0).getUserId());
        assertEquals(boardId, board.getBoardSessions().get(0).getBoardId());
        assertEquals(2, eventListener.getEventCount());
    }
}