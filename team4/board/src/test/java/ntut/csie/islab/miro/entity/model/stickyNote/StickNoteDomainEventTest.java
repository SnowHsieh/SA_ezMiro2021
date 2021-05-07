package ntut.csie.islab.miro.entity.model.stickyNote;

import ntut.csie.islab.miro.entity.model.textFigure.TextFigure;
import ntut.csie.islab.miro.entity.model.textFigure.Position;
import ntut.csie.islab.miro.entity.model.textFigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.textFigure.Style;
import ntut.csie.islab.miro.entity.model.textFigure.stickynote.StickyNote;
import ntut.csie.islab.miro.entity.model.textFigure.stickynote.event.*;
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
    public void change_stickyNote_content_then_publishes_a_stickyNote_content_changed_domain_event() {
        TextFigure stickyNote = createStickyNote();
        stickyNote.clearDomainEvents();
        assertEquals(0, stickyNote.getDomainEvents().size());

        String newContent = "newDescription";
        String oldContent = stickyNote.getContent();
        stickyNote.changeContent(newContent);

        assertEquals(newContent, stickyNote.getContent());
        assertEquals(1, stickyNote.getDomainEvents().size());
        assertEquals(StickyNoteContentChangedDomainEvent.class, stickyNote.getDomainEvents().get(0).getClass());

        StickyNoteContentChangedDomainEvent stickyNoteContentChangedDomainEvent = (StickyNoteContentChangedDomainEvent) stickyNote.getDomainEvents().get(0);

        assertEquals(stickyNote.getBoardId(), stickyNoteContentChangedDomainEvent.getBoardId());
        assertEquals(stickyNote.getFigureId(), stickyNoteContentChangedDomainEvent.getFigureId());
        assertEquals(newContent, stickyNoteContentChangedDomainEvent.getNewContent());
        assertEquals(oldContent, stickyNoteContentChangedDomainEvent.getOriginalContent());

    }

    @Test
    public void change_stickyNote_color_then_publishes_a_stickyNote_color_changed_domain_event() {
        TextFigure stickyNote = createStickyNote();
        stickyNote.clearDomainEvents();
        assertEquals(0, stickyNote.getDomainEvents().size());

        String newColor = "#f9f900";
        String originalColor = stickyNote.getStyle().getColor();
        stickyNote.changeColor(newColor);

        assertEquals(newColor, stickyNote.getStyle().getColor());
        assertEquals(1, stickyNote.getDomainEvents().size());
        assertEquals(StickyNoteColorChangedDomainEvent.class, stickyNote.getDomainEvents().get(0).getClass());

        StickyNoteColorChangedDomainEvent stickyNoteColorChangedDomainEvent = (StickyNoteColorChangedDomainEvent) stickyNote.getDomainEvents().get(0);

        assertEquals(stickyNote.getBoardId(), stickyNoteColorChangedDomainEvent.getBoardId());
        assertEquals(stickyNote.getFigureId(), stickyNoteColorChangedDomainEvent.getFigureId());
        assertEquals(newColor, stickyNoteColorChangedDomainEvent.getNewColor());
        assertEquals(originalColor, stickyNoteColorChangedDomainEvent.getOriginalColor());

    }


    @Test
    public void resize_stickyNote_then_publishes_a_stickyNote_resized_domain_event() {
        TextFigure stickyNote = createStickyNote();
        stickyNote.clearDomainEvents();
        assertEquals(0, stickyNote.getDomainEvents().size());

        Double oldWidth = stickyNote.getStyle().getWidth();;
        Double oldHeight = stickyNote.getStyle().getHeight();;

        Double newHeight = 100.0;
        Double newWidth = 100.0;

        stickyNote.resize(newWidth, newHeight);

        assertEquals(newWidth, stickyNote.getStyle().getWidth());
        assertEquals(newHeight, stickyNote.getStyle().getHeight());
        assertEquals(1, stickyNote.getDomainEvents().size());
        assertEquals(StickyNoteResizedDomainEvent.class, stickyNote.getDomainEvents().get(0).getClass());

        StickyNoteResizedDomainEvent stickyNoteResizedDomainEvent = (StickyNoteResizedDomainEvent) stickyNote.getDomainEvents().get(0);

        assertEquals(stickyNote.getBoardId(), stickyNoteResizedDomainEvent.getBoardId());
        assertEquals(stickyNote.getFigureId(), stickyNoteResizedDomainEvent.getFigureId());
        assertEquals(newWidth, stickyNoteResizedDomainEvent.getNewWidth());
        assertEquals(newHeight, stickyNoteResizedDomainEvent.getNewHeight());
        assertEquals(oldWidth, stickyNoteResizedDomainEvent.getOldWidth());
        assertEquals(oldHeight, stickyNoteResizedDomainEvent.getOldHeight());

    }


    @Test
    public void move_a_stickyNote_then_publishes_a_stickyNote_moved_domain_event() {
        TextFigure stickyNote = createStickyNote();
        stickyNote.clearDomainEvents();
        assertEquals(0, stickyNote.getDomainEvents().size());

        Position newPosition = new Position(100,100);
        Position oldPosition = stickyNote.getPosition();
        stickyNote.changePosition(newPosition);

        assertEquals(newPosition, stickyNote.getPosition());

        assertEquals(1, stickyNote.getDomainEvents().size());
        assertEquals(StickyNoteMovedDomainEvent.class, stickyNote.getDomainEvents().get(0).getClass());

        StickyNoteMovedDomainEvent stickyNoteMovedDomainEvent = (StickyNoteMovedDomainEvent) stickyNote.getDomainEvents().get(0);

        assertEquals(stickyNote.getBoardId(), stickyNoteMovedDomainEvent.getBoardId());
        assertEquals(stickyNote.getFigureId(), stickyNoteMovedDomainEvent.getFigureId());
        assertEquals(newPosition, stickyNoteMovedDomainEvent.getNewPosition());
        assertEquals(oldPosition, stickyNoteMovedDomainEvent.getOriginalPosition());

    }
}
