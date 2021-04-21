package ntut.csie.sslab.kanban.entity.model.workspace;

import ntut.csie.sslab.ddd.model.AggregateRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Workspace extends AggregateRoot<String> {

    private String boardId;
    private String workspaceId;
    private List<Figure> figures;

    public Workspace(String workspaceId, String boardId) {
        super(workspaceId);
        this.boardId = boardId;
        this.workspaceId = workspaceId;
        this.figures = new ArrayList<>();
    }

    public String getBoardId() {
        return boardId;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public List<Figure> getAllFigures() {
        return figures;
    }
}
