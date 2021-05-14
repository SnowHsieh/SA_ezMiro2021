package ntut.csie.team5.entity.model.board.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.team5.entity.model.board.BoardSessionId;

public class BoardEntered extends DomainEvent {

    private String boardId;
    private String userId;
    private BoardSessionId boardSessionId;

    public BoardEntered(String boardId, String userId, BoardSessionId boardSessionId) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.userId = userId;
        this.boardSessionId = boardSessionId;
    }

    public String boardId() {
        return boardId;
    }

    public String userId() {
        return userId;
    }

    public BoardSessionId boardSessionId() {
        return boardSessionId;
    }
}
