package ntut.csie.selab.entity.model.widget;

import ntut.csie.selab.entity.model.widget.event.LineDisconnected;
import ntut.csie.selab.entity.model.widget.event.LineLinked;
import ntut.csie.selab.entity.model.widget.event.LinkedStickyNoteIdDeleted;

import java.util.Date;

public class Line extends Widget {

    private String headWidgetId;
    private String tailWidgetId;

    public Line(String id, String boardId, Position position) {
        super(id, boardId, position, WidgetType.LINE.getType());
    }

    public String getHeadWidgetId() {
        return headWidgetId;
    }

    public void setHeadWidgetId(String headWidgetId) {
        this.headWidgetId = headWidgetId;
    }

    public String getTailWidgetId() {
        return tailWidgetId;
    }

    public void setTailWidgetId(String tailWidgetId) {
        this.tailWidgetId = tailWidgetId;
    }

    public void link(String endPoint, String widgetId) {
        if(endPoint.equals("head")) {
            headWidgetId = widgetId;
        } else {
            tailWidgetId = widgetId;
        }

        addDomainEvent((new LineLinked(
                new Date(),
                this.boardId,
                getId(),
                widgetId,
                endPoint
        )));
    }

    public void disconnectWidgetById(String widgetId) {
        if(headWidgetId != null && headWidgetId.equals(widgetId)) {
            this.headWidgetId = null;
        }
        if(tailWidgetId != null && tailWidgetId.equals(widgetId)) {
            this.tailWidgetId = null;
        }

        addDomainEvent(new LinkedStickyNoteIdDeleted(
                new Date()
        ));
    }

    public void disconnectWidgetByEndPoint(String lineEndPoint) {
        if (lineEndPoint.equals(LineEndPoint.HEAD.getEndPoint())) {
            this.headWidgetId = null;
        } else if (lineEndPoint.equals(LineEndPoint.TAIL.getEndPoint())) {
            this.tailWidgetId = null;
        } else {
            throw new RuntimeException();
        }

        addDomainEvent(new LineDisconnected(
                new Date(),
                this.boardId,
                this.id,
                lineEndPoint
        ));
    }
}
