package ntut.csie.team5.entity.model.project;

import ntut.csie.sslab.ddd.model.ValueObject;

public class CommittedBoard extends ValueObject {

    private String boardId;
    private String projectId;

    public CommittedBoard(String boardId, String projectId) {
        this.boardId = boardId;
        this.projectId = projectId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
