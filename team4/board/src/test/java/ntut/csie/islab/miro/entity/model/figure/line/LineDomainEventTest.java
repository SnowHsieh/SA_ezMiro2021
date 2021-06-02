package ntut.csie.islab.miro.entity.model.figure.line;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.figure.Figure;
import ntut.csie.islab.miro.entity.model.figure.line.Line;
import ntut.csie.islab.miro.entity.model.figure.line.event.LineCreatedDomainEvent;
import ntut.csie.islab.miro.entity.model.figure.line.event.LineDeletedDomainEvent;
import ntut.csie.islab.miro.entity.model.figure.line.event.TextfigureAttachedByLineDomainEvent;
import ntut.csie.islab.miro.entity.model.figure.textfigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.figure.textfigure.Style;
import ntut.csie.islab.miro.entity.model.figure.textfigure.TextFigure;
import ntut.csie.islab.miro.entity.model.figure.textfigure.stickynote.StickyNote;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LineDomainEventTest {

    @Test
    public void create_a_line_then_publishes_a_line_created_domain_event() {
        UUID boardId = UUID.randomUUID();
        List<Position> positionList = new ArrayList<>();
        int strokeWidth = 5;
        String color = "#000000";
        Figure line = new Line(boardId, positionList, strokeWidth, color);

        assertEquals(1, line.getDomainEvents().size());
        assertEquals(LineCreatedDomainEvent.class, line.getDomainEvents().get(0).getClass());
    }

    @Test
    public void delete_a_line_then_publishes_a_line_deleted_domain_event(){
        UUID boardId = UUID.randomUUID();
        List<Position> positionList = new ArrayList<>();
        int strokeWidth = 5;
        String color = "#000000";
        Figure line = new Line(boardId, positionList, strokeWidth, color);

        line.markAsRemoved(line.getBoardId(), line.getFigureId());

        assertEquals(2, line.getDomainEvents().size());
        assertEquals(LineDeletedDomainEvent.class, line.getDomainEvents().get(1).getClass());
    }
    @Test
    public void line_attach_a_textfigure_then_publishes_a_textfigure_attached_by_line_domain_event(){
        UUID boardId = UUID.randomUUID();
        List<Position> positionList = new ArrayList<>();
        int strokeWidth = 5;
        String color = "#000000";
        Figure line = new Line(boardId, positionList, strokeWidth, color);
        TextFigure sn = new StickyNote(UUID.randomUUID(), new Position(1.0, 1.0), "content", new Style(10, ShapeKindEnum.TRIANGLE, 87.2, 100, "#123456"));
        line.attachTextFigure(sn.getFigureId());

        assertEquals(2, line.getDomainEvents().size());
        assertEquals(TextfigureAttachedByLineDomainEvent.class, line.getDomainEvents().get(1).getClass());
    }

}
