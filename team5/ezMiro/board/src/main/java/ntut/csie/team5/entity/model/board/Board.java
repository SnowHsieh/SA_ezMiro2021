package ntut.csie.team5.entity.model.board;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.team5.entity.model.board.event.BoardCreated;

public class Board extends AggregateRoot<String> {
    private String name;
    private String projectId;

    public Board(String boardId, String name, String projectId) {
        super(boardId);
        this.name = name;
        this.projectId = projectId;

        addDomainEvent(new BoardCreated(boardId, name, projectId));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBoardId() {
        return getId();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
