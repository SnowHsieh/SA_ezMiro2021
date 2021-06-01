package ntut.csie.selab.entity.model.widget.event;

import ntut.csie.selab.model.DomainEvent;

import java.util.Date;

public class WidgetCreated extends DomainEvent {
    private String boardId;
    private String widgetId;
    private String type;

    public WidgetCreated(Date occurredOn, String boardId, String widgetId, String type) {
        super(occurredOn);
        this.boardId = boardId;
        this.widgetId = widgetId;
        this.type = type;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getWidgetId() {
        return widgetId;
    }

    public String getType() {
        return type;
    }
}
