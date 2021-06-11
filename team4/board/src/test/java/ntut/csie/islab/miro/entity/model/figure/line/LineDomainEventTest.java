package ntut.csie.islab.miro.entity.model.figure.line;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.figure.Figure;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.ConnectableFigure;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.Style;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.stickynote.StickyNote;
import ntut.csie.islab.miro.entity.model.figure.line.event.ConnectableFigureAttachedByLineDomainEvent;
import ntut.csie.islab.miro.entity.model.figure.line.event.ConnectableFigureUnattachedDomainEvent;
import ntut.csie.islab.miro.entity.model.figure.line.event.LineCreatedDomainEvent;
import ntut.csie.islab.miro.entity.model.figure.line.event.LineDeletedDomainEvent;

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
    public void line_attach_a_connectableFigure_then_publishes_a_connectableFigure_attached_by_line_domain_event(){
        UUID boardId = UUID.randomUUID();
        List<Position> positionList = new ArrayList<>();
        int strokeWidth = 5;
        String color = "#000000";
        Figure line = new Line(boardId, positionList, strokeWidth, color);
        ConnectableFigure sn = new StickyNote(UUID.randomUUID(), new Position(1.0, 1.0), "content",87.2,100, new Style(10, ShapeKindEnum.TRIANGLE, "#123456"));
        line.attachConnectableFigure(sn.getFigureId(),"source");

        assertEquals(2, line.getDomainEvents().size());
        assertEquals(ConnectableFigureAttachedByLineDomainEvent.class, line.getDomainEvents().get(1).getClass());
    }
    @Test
    public void line_unattach_a_connectable_figure_then_publishes_a_connectable_figure_unattached_by_line_domain_event(){
        UUID boardId = UUID.randomUUID();
        List<Position> positionList = new ArrayList<>();
        int strokeWidth = 5;
        String color = "#000000";
        Figure line = new Line(boardId, positionList, strokeWidth, color);
        ConnectableFigure sn = new StickyNote(UUID.randomUUID(), new Position(1.0, 1.0), "content",87.2,100, new Style(10, ShapeKindEnum.TRIANGLE, "#123456"));
        line.attachConnectableFigure(sn.getFigureId(),"source");
        line.unattachConnectableFigure("source");

        assertEquals(3, line.getDomainEvents().size());
        assertEquals(ConnectableFigureUnattachedDomainEvent.class, line.getDomainEvents().get(2).getClass());
    }


}
