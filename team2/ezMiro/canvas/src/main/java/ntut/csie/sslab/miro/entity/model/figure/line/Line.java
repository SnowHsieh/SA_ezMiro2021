package ntut.csie.sslab.miro.entity.model.figure.line;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.miro.entity.model.figure.Figure;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.line.event.LineEvents;
import java.util.UUID;

public class Line extends Figure {
    private String startConnectableFigureId;
    private String endConnectableFigureId;
    private Coordinate startOffset;
    private Coordinate endOffset;
    private ArrowStyle startArrowStyle;
    private ArrowStyle endArrowStyle;

    public Line(String lineId, String boardId, String startConnectableFigureId, String endConnectableFigureId,
                Coordinate startOffset, Coordinate endOffset, ArrowStyle startArrowStyle, ArrowStyle endArrowStyle) {
        super(lineId, boardId);
        this.startConnectableFigureId = startConnectableFigureId;
        this.endConnectableFigureId = endConnectableFigureId;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.startArrowStyle = startArrowStyle;
        this.endArrowStyle = endArrowStyle;
        addDomainEvent(new LineEvents.LineCreated(UUID.randomUUID(), boardId, lineId, startConnectableFigureId, endConnectableFigureId,
                       startOffset, endOffset, startArrowStyle, endArrowStyle, DateProvider.now()));
    }

    public String getStartConnectableFigureId() {
        return startConnectableFigureId;
    }

    public void setStartConnectableFigureId(String startConnectableFigureId) {
        this.startConnectableFigureId = startConnectableFigureId;
    }

    public String getEndConnectableFigureId() {
        return endConnectableFigureId;
    }

    public void setEndConnectableFigureId(String endConnectableFigureId) {
        this.endConnectableFigureId = endConnectableFigureId;
    }

    public Coordinate getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(Coordinate startOffset) {
        this.startOffset = startOffset;
    }

    public Coordinate getEndOffset() {
        return endOffset;
    }

    public void setEndOffset(Coordinate endOffset) {
        this.endOffset = endOffset;
    }

    public ArrowStyle getStartArrowStyle() {
        return startArrowStyle;
    }

    public void setStartArrowStyle(ArrowStyle startArrowStyle) {
        this.startArrowStyle = startArrowStyle;
    }

    public ArrowStyle getEndArrowStyle() {
        return endArrowStyle;
    }

    public void setEndArrowStyle(ArrowStyle endArrowStyle) {
        this.endArrowStyle = endArrowStyle;
    }

    public void move(Coordinate delta) {
        Coordinate newStartOffset = new Coordinate(startOffset.getX() + delta.getX(), startOffset.getY() + delta.getY());
        Coordinate newEndOffset = new Coordinate(endOffset.getX() + delta.getX(), endOffset.getY() + delta.getY());
        this.setStartOffset(newStartOffset);
        this.setEndOffset(newEndOffset);
        addDomainEvent(new LineEvents.LineMoved(UUID.randomUUID(), getId(), newStartOffset, newEndOffset, getBoardId(), DateProvider.now()));
    }

    public void markAsRemoved(String boardId) {
        addDomainEvent(new LineEvents.LineDeleted(UUID.randomUUID(), getId(), boardId, DateProvider.now()));
    }

    public void movePoint(LinePoint linePoint, Coordinate pointDelta) {
        Coordinate newOffset;
        if (linePoint == LinePoint.START) {
            newOffset = new Coordinate(startOffset.getX() + pointDelta.getX(), startOffset.getY() + pointDelta.getY());
            this.setStartOffset(newOffset);
        } else {
            newOffset = new Coordinate(endOffset.getX() + pointDelta.getX(), endOffset.getY() + pointDelta.getY());
            this.setEndOffset(newOffset);
        }
        addDomainEvent(new LineEvents.LinePointMoved(UUID.randomUUID(), getId(), linePoint, newOffset, getBoardId(), DateProvider.now()));
    }

    public void connectToFigure(LinePoint linePoint, String figureId, Coordinate offset) {
        if (linePoint == LinePoint.START) {
            this.setStartConnectableFigureId(figureId);
            this.setStartOffset(offset);
        } else {
            this.setEndConnectableFigureId(figureId);
            this.setEndOffset(offset);
        }
        addDomainEvent(new LineEvents.LineConnectedToFigure(UUID.randomUUID(), getId(), linePoint, figureId, offset, getBoardId(), DateProvider.now()));
    }

    public void disconnectFromFigure(LinePoint linePoint, String figureId, Coordinate pointOffset) {
        if (linePoint == LinePoint.START) {
            this.setStartConnectableFigureId("");
            this.setStartOffset(pointOffset);
        } else {
            this.setEndConnectableFigureId("");
            this.setEndOffset(pointOffset);
        }
        addDomainEvent(new LineEvents.LineDisconnectFromFigure(UUID.randomUUID(), getId(), linePoint, figureId, pointOffset, getBoardId(), DateProvider.now()));
    }
}