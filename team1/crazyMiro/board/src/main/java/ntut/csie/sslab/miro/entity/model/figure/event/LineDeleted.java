package ntut.csie.sslab.miro.entity.model.figure.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;


public class LineDeleted extends DomainEvent {
    private final String lineId;
    private final String boardId;

    public LineDeleted( String lineId, String boardId) {
        super(DateProvider.now());
        this.lineId = lineId;
        this.boardId = boardId;
    }

    public String getLineId() {
        return lineId;
    }

    public String getBoardId() {
        return boardId;
    }
}
