package ntut.csie.selab.entity.model.board.event;

import ntut.csie.selab.model.DomainEvent;

import java.util.Date;

public class BoardLeft extends DomainEvent {
    private String boardId;
    private String userId;

    public BoardLeft(Date date, String boardId, String userId) {
        super(date);
        this.boardId = boardId;
        this.userId = userId;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getUserId() {
        return userId;
    }
}
