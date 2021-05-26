package ntut.csie.selab.entity.model.widget.event;

import ntut.csie.selab.model.DomainEvent;

import java.util.Date;

public class WidgetZOrderChanged extends DomainEvent {

    private String boardId;

    public WidgetZOrderChanged(Date occurredOn, String boardId) {
        super(occurredOn);
        this.boardId = boardId;
    }

    public String getBoardId() {
        return boardId;
    }
}
