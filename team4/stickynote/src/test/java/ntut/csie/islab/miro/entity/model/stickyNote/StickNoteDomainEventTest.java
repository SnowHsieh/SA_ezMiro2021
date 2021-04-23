package ntut.csie.islab.miro.entity.model.stickyNote;

import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.figure.entity.model.figure.Figure;
import ntut.csie.islab.miro.figure.entity.model.figure.Position;
import ntut.csie.islab.miro.figure.entity.model.figure.ShapeKindEnum;
import ntut.csie.islab.miro.figure.entity.model.figure.Style;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StickNoteDomainEventTest {
    private Figure createStickyNote(){
        return new StickyNote(UUID.randomUUID(), new Position(1.0,1.0) , "content", new Style(10, ShapeKindEnum.TRIANGLE,87.2,"#123456"));
    }
    @Test
    public void create_a_stickyNote_then_publishes_a_stickyNote_created_domain_event(){
        Figure stickyNote = createStickyNote();
        assertEquals(1,stickyNote.getDomainEvents().size());
    }
}
