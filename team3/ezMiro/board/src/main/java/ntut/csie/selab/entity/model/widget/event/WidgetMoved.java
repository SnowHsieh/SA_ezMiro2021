package ntut.csie.selab.entity.model.widget.event;

import ntut.csie.selab.model.DomainEvent;

import java.util.Date;

public class WidgetMoved extends DomainEvent {
    private String widgetId;
    private String boardId;

    public WidgetMoved(Date occurredOn, String boardId, String widgetId) {
        super(occurredOn);
        this.boardId = boardId;
        this.widgetId = widgetId;
    }

    public String getWidgetId() {
        return widgetId;
    }

    public String getBoardId() {
        return boardId;
    }
}
