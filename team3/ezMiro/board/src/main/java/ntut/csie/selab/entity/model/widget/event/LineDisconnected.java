package ntut.csie.selab.entity.model.widget.event;

import ntut.csie.selab.model.DomainEvent;

import java.util.Date;

public class LineDisconnected extends DomainEvent {
    private String boardId;
    private String lineId;
    private String lineEndPoint;

    public LineDisconnected(Date occurredOn, String boardId, String lineId, String lineEndPoint) {
        super(occurredOn);
        this.boardId = boardId;
        this.lineId = lineId;
        this.lineEndPoint = lineEndPoint;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getLineId() {
        return lineId;
    }

    public String getLineEndPoint() {
        return lineEndPoint;
    }
}
