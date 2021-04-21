package ntut.csie.selab.usecase.board.create;

import ntut.csie.selab.model.DomainEvent;

import java.util.Date;

public class BoardCreated extends DomainEvent {

    private String boardId;
    private String teamId;

    public BoardCreated(Date occurredOn, String boardId, String teamId) {
        super(occurredOn);
        this.boardId = boardId;
        this.teamId = teamId;
    }
}
