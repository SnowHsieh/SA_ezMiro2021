package ntut.csie.sslab.kanban.entity.model.workflow.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class LaneRenamed extends DomainEvent {

    private final String workflowId;
    private final String laneId;
    private final String oldName;
    private final String newName;
    private final String userId;
    private final String username;
    private final String boardId;
    private final String layout;

    public LaneRenamed(String workflowId, String laneId, String oldName, String newName, String userId, String username, String boardId, String layout) {
        super(DateProvider.now());
        this.workflowId = workflowId;
        this.laneId = laneId;
        this.oldName = oldName;
        this.newName = newName;
        this.userId = userId;
        this.username = username;
        this.boardId = boardId;
        this.layout = layout;
    }

    public String workflowId() {
        return workflowId;
    }

    public String laneId() {
        return laneId;
    }

    public String oldName() {
        return oldName;
    }

    public String newName() {
        return newName;
    }

    public String userId() { return userId; }

    public String username() { return username; }

    public String boardId() { return boardId; }

    public String layout() {
        return layout;
    }
}
