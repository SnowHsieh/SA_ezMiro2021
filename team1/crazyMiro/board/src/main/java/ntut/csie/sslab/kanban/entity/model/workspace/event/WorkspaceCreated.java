package ntut.csie.sslab.kanban.entity.model.workspace.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.Date;

public class WorkspaceCreated extends DomainEvent {
    private final String boardId;
    private final String workspaceId;

    public WorkspaceCreated(String boardId, String workspaceId) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.workspaceId = workspaceId;
    }
}
