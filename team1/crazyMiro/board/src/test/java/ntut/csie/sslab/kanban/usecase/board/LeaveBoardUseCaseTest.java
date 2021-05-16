package ntut.csie.sslab.kanban.usecase.board;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.kanban.entity.model.board.Board;
import ntut.csie.sslab.kanban.entity.model.board.BoardSession;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;

import ntut.csie.sslab.kanban.usecase.board.leave.LeaveBoardInput;
import ntut.csie.sslab.kanban.usecase.board.leave.LeaveBoardUseCase;
import ntut.csie.sslab.kanban.usecase.board.leave.LeaveBoardUseCaseImpl;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class LeaveBoardUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void user_leave_board() {
        String boardId = UUID.randomUUID().toString();
        createBoard(boardId, "Jay board");
        eventListener.clearEventCount();
        String userId = "user1";
        String boardSessionId = enterBoard(boardId, userId);
        eventListener.clearEventCount();
        LeaveBoardUseCase leaveBoardUseCase = new LeaveBoardUseCaseImpl(boardRepository, domainEventBus);
        LeaveBoardInput input = leaveBoardUseCase.newInput();
        CqrsCommandOutput output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setBoardSessionId(boardSessionId);

        leaveBoardUseCase.execute(input, output);

        Board board = boardRepository.findById(boardId).get();
        List<BoardSession> boardSessions = board.getBoardSessions();
        assertEquals(1, boardSessions.size());
        assertFalse(boardSessions.stream().anyMatch(x->x.getBoardSessionId().equals(boardSessionId)));
        assertEquals(2,eventListener.getEventCount());
    }


}