package ntut.csie.team5.entity.model.board.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class BoardCreated extends DomainEvent {

    private final String boardId;
    private final String name;
    private final String projectId;

    public BoardCreated(String boardId, String name, String projectId) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.name = name;
        this.projectId = projectId;
    }

    public String boardId() {
        return boardId;
    }

    public String name() {return name;}

    public String projectId() { return projectId; }
}
