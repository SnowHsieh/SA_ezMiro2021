package ntut.csie.team5.entity.model.project.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class BoardCommitted extends DomainEvent {

    private String boardId;
    private String projectId;

    public BoardCommitted(String boardId, String projectId) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.projectId = projectId;
    }

    public String boardId() {
        return boardId;
    }

    public String projectId() {
        return projectId;
    }
}
