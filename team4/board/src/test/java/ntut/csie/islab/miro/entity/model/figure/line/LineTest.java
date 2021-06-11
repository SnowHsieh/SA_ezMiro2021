package ntut.csie.islab.miro.entity.model.figure.line;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.figure.Figure;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.Style;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.ConnectableFigure;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.stickynote.StickyNote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LineTest {

    @BeforeEach
    void setUp() {

    }

    @Test
    public void create_a_line() {

        UUID boardId = UUID.randomUUID();
        List<Position> positionList = new ArrayList<>();

        int strokeWidth = 5;
        String color = "#000000";
        Figure line = new Line(boardId, positionList, strokeWidth, color);
        assertEquals(boardId, line.getBoardId());
        assertEquals(positionList.size(), line.getPositionList().size());
        assertEquals(ArrowKindEnum.NONE, line.getSrcArrowKind());
        assertEquals(ArrowKindEnum.NONE, line.getDestArrowKind());
        assertEquals(strokeWidth, line.getStrokeWidth());
        assertEquals(color, line.getColor());

        ArrowKindEnum srcArrowKind = ArrowKindEnum.NONE; //NONE,CIRCLE,ARROW
        ArrowKindEnum destArrowKind = ArrowKindEnum.ARROW; //NONE,CIRCLE,ARROW
        line.setSrcArrowKind(srcArrowKind);
        line.setDestArrowKind(destArrowKind);
        assertEquals(srcArrowKind, line.getSrcArrowKind());
        assertEquals(destArrowKind, line.getDestArrowKind());

    }

    @Test
    public void test_a_line_attach_connectableFigure() {
        UUID boardId = UUID.randomUUID();
        List<Position> positionList = new ArrayList<>();
        int strokeWidth = 5;
        String color = "#000000";
        Figure line = new Line(boardId, positionList, strokeWidth, color);
        ConnectableFigure sn = new StickyNote(UUID.randomUUID(), new Position(1.0, 1.0), "content", 87.2, 100, new Style(10, ShapeKindEnum.TRIANGLE, "#123456"));
        line.attachConnectableFigure(sn.getFigureId(), "source");
        assertEquals(sn.getFigureId(), line.getSrcConnectableFigureId());
    }

    @Test
    public void test_line_unattach_from_connectableFigure() {
        UUID boardId = UUID.randomUUID();
        List<Position> positionList = new ArrayList<>();
        int strokeWidth = 5;
        String color = "#000000";
        Figure line = new Line(boardId, positionList, strokeWidth, color);
        ConnectableFigure sn = new StickyNote(UUID.randomUUID(), new Position(1.0, 1.0), "content", 87.2, 100, new Style(10, ShapeKindEnum.TRIANGLE, "#123456"));
        line.attachConnectableFigure(sn.getFigureId(), "source");
        assertEquals(sn.getFigureId(), line.getSrcConnectableFigureId());
        line.unattachConnectableFigure("source");
        assertEquals(null, line.getSrcConnectableFigureId());
    }
}
