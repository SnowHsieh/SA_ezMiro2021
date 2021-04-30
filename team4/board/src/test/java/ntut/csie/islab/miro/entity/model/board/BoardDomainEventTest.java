package ntut.csie.islab.miro.entity.model.board;

import ntut.csie.islab.miro.entity.model.board.event.TextFigureCommittedDomainEvent;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

public class BoardDomainEventTest {
    private Board createBoard(){
        return new Board(UUID.randomUUID(), "BoardName");
    }
    @Test
    public void create_a_board_then_publishes_a_board_created_domain_event(){
        Board board = createBoard();
        assertEquals(1,board.getDomainEvents().size());

    }
    @Test
    public void commit_a_figure_then_publishes_a_figure_committed_domain_event() {
        Board board = createBoard();
        board.clearDomainEvents();
        UUID figureId = UUID.randomUUID();
        board.commitFigure(figureId);
        assertEquals(1,board.getDomainEvents().size());
        TextFigureCommittedDomainEvent textFigureCommittedDomainEvent = (TextFigureCommittedDomainEvent) board.getDomainEvents().get(0);
        assertEquals(board.getBoardId(), textFigureCommittedDomainEvent.getBoardId());
        assertEquals(figureId, textFigureCommittedDomainEvent.getTextFigureId());
    }


    }
