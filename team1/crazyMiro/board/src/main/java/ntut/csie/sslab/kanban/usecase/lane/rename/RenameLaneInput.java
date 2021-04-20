package ntut.csie.sslab.kanban.usecase.lane.rename;

import ntut.csie.sslab.ddd.usecase.Input;

public interface RenameLaneInput extends Input {

    String getBoardId();

    void setBoardId(String boardId);

    String getWorkflowId();

    void setWorkflowId(String workflowId);

    String getLaneId();

    void setLaneId(String laneId);

    String getNewName();

    void setNewName(String newTitle);

    String getUserId();

    void setUserId(String userId);

    String getUsername();

    void setUsername(String username);
}
