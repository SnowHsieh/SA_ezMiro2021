package ntut.csie.selab.entity.model.board.event;

import ntut.csie.selab.model.DomainEvent;

import java.util.Date;

public class WidgetCreationCommitted extends DomainEvent {

    private String boardId;
    private String widgetId;

    public WidgetCreationCommitted(Date occurredOn, String boardId, String widgetId) {
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
