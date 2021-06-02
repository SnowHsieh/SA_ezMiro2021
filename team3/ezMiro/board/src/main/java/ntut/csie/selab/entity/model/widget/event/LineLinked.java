package ntut.csie.selab.entity.model.widget.event;

import ntut.csie.selab.model.DomainEvent;

import java.util.Date;

public class LineLinked extends DomainEvent {
    private String boardId;
    private String widgetId;

    public LineLinked(Date date, String boardId, String widgetId) {
        super(date);
        this.boardId = boardId;
        this.widgetId = widgetId;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getWidgetId() {
        return widgetId;
    }
}
