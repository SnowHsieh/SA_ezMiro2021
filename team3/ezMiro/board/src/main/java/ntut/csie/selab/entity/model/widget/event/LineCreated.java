package ntut.csie.selab.entity.model.widget.event;

import ntut.csie.selab.model.DomainEvent;

import java.util.Date;

public class LineCreated extends DomainEvent {

    private String boardId;
    private String lineId;

    public LineCreated(Date occurredOn, String boardId, String lineId) {
        super(occurredOn);
        this.boardId = boardId;
        this.lineId = lineId;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getLineId() {
        return lineId;
    }
}
