package ntut.csie.selab.entity.model.widget.event;

import ntut.csie.selab.model.DomainEvent;

import java.util.Date;

public class WidgetDeleted extends DomainEvent {
    private String boardId;
    private String widgetId;

    public WidgetDeleted(Date occurredOn, String boardId, String widgetId) {
        super(occurredOn);
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
