package ntut.csie.selab.entity.model.widget.event;

import ntut.csie.selab.model.DomainEvent;

import java.util.Date;

public class LineLinked extends DomainEvent {
    private String boardId;
    private String lineId;
    private String widgetId;
    private String endPoint;

    public LineLinked(Date occurredOn, String boardId, String lineId, String widgetId, String endPoint) {
        super(occurredOn);
        this.boardId = boardId;
        this.lineId = lineId;
        this.widgetId = widgetId;
        this.endPoint = endPoint;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getLineId() {
        return lineId;
    }

    public String getWidgetId() {
        return widgetId;
    }

    public String getEndPoint() {
        return endPoint;
    }
}
