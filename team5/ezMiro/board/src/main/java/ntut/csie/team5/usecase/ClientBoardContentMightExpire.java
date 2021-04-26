package ntut.csie.team5.usecase;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class ClientBoardContentMightExpire extends DomainEvent {

    private String boardId;

    public ClientBoardContentMightExpire(String boardId) {
        super(DateProvider.now());
        this.boardId = boardId;
    }

    public String boardId() {
        return boardId;
    }
}
