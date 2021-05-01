package ntut.csie.islab.miro.entity.model.stickyNote;

import ntut.csie.islab.miro.entity.model.textFigure.TextFigure;
import ntut.csie.islab.miro.entity.model.textFigure.Position;
import ntut.csie.islab.miro.entity.model.textFigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.textFigure.Style;
import ntut.csie.islab.miro.entity.model.textFigure.stickynote.StickyNote;
import ntut.csie.islab.miro.entity.model.textFigure.stickynote.event.StickyNoteEditedDomainEvent;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StickNoteDomainEventTest {
    private TextFigure createStickyNote() {
        return new StickyNote(UUID.randomUUID(), new Position(1.0, 1.0), "content", new Style(10, ShapeKindEnum.TRIANGLE, 87.2, 100, "#123456"));
    }

    @Test
    public void create_a_stickyNote_then_publishes_a_stickyNote_created_domain_event() {
        TextFigure stickyNote = createStickyNote();
        assertEquals(1, stickyNote.getDomainEvents().size());
    }

    @Test
    public void delete_a_stickyNote_then_publishes_a_stickyNote_deleted_domain_event() {
        TextFigure stickyNote = createStickyNote();
        assertEquals(1, stickyNote.getDomainEvents().size());

        stickyNote.markAsRemoved(stickyNote.getBoardId(), stickyNote.getFigureId());
        assertEquals(2, stickyNote.getDomainEvents().size());
    }

    @Test
    public void edit_a_stickyNote_then_publishes_a_stickyNote_edited_domain_event() {
        TextFigure stickyNote = createStickyNote();
        stickyNote.clearDomainEvents();
        assertEquals(0, stickyNote.getDomainEvents().size());

        String newContent = "newDescription";
        String oldContent = stickyNote.getContent();
        stickyNote.changeContent(newContent);

        assertEquals(newContent, stickyNote.getContent());
        assertEquals(1, stickyNote.getDomainEvents().size());
        assertEquals(StickyNoteEditedDomainEvent.class, stickyNote.getDomainEvents().get(0).getClass());

        StickyNoteEditedDomainEvent stickyNoteEditedDomainEvent = (StickyNoteEditedDomainEvent) stickyNote.getDomainEvents().get(0);

        assertEquals(stickyNote.getBoardId(), stickyNoteEditedDomainEvent.getBoardId());
        assertEquals(stickyNote.getFigureId(), stickyNoteEditedDomainEvent.getFigureId());
        assertEquals(newContent, stickyNoteEditedDomainEvent.getNewContent());
        assertEquals(oldContent, stickyNoteEditedDomainEvent.getOriginalContent());

    }
}
