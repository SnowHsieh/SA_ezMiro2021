package ntut.csie.sslab.miro.usecase.eventhandler;

import ntut.csie.sslab.miro.entity.model.board.event.BoardEntered;
import ntut.csie.sslab.miro.entity.model.board.event.BoardLeft;
import ntut.csie.sslab.miro.entity.model.cursor.event.CursorCreated;
import ntut.csie.sslab.miro.entity.model.cursor.event.CursorDeleted;
import ntut.csie.sslab.miro.usecase.AbstractSpringBootJpaTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardEventBroadcastTest extends AbstractSpringBootJpaTest {

    @Test
    public void user_enter_board_broadcasts_board_enter_event_and_cursor_create_event() {
        String boardId = UUID.randomUUID().toString();
        createBoard(boardId, "jay board");
        eventListener.clearEventCount();
        String userId = "user1";
        eventListener.clearEventCount();

        enterBoard(boardId, userId);

        assertEquals(2, eventListener.getEventCount());
        assertTrue(eventListener.getEvent(0) instanceof BoardEntered);
        assertTrue(eventListener.getEvent(1) instanceof CursorCreated);
    }

    @Test
    public void user_leave_board_broadcasts_board_left_event_and_cursor_deleted_event() {
        String boardId = UUID.randomUUID().toString();
        createBoard(boardId, "Jay board");
        eventListener.clearEventCount();
        String userId = "user1";
        String boardSessionId = enterBoard(boardId, userId);
        eventListener.clearEventCount();

        leaveBoard(boardId, boardSessionId);

        assertEquals(2,eventListener.getEventCount());
        assertTrue(eventListener.getEvent(0) instanceof BoardLeft);
        assertTrue(eventListener.getEvent(1) instanceof CursorDeleted);
    }
}
