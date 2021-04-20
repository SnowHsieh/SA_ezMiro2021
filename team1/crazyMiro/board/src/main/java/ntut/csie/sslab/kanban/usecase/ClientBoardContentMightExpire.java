package ntut.csie.sslab.kanban.usecase;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

import java.util.Date;

public class ClientBoardContentMightExpire extends DomainEvent {
    private Date occurredOn;
    private String boardId;

    public ClientBoardContentMightExpire(String boardId) {
        super(DateProvider.now());
        this.boardId = boardId;
    }

    @Override
    public Date occurredOn() {
        return occurredOn;
    }

    public String boardId() {
        return boardId;
    }
}
