package ntut.csie.sslab.kanban.usecase.lane.stage.create;

import ntut.csie.sslab.ddd.usecase.Input;

public interface CreateStageInput extends Input {

    String getStageName();

    void setStageName(String stageName);

    String getBoardId();

    void setBoardId(String boardId);

    String getWorkflowId();

    void setWorkflowId(String workflowId);

    void setWipLimit(int wip);

    int getWipLimit();

    void setLaneType(String laneType);

    String getLaneType();

    void setParentId(String parentId);

    String getParentId();

    void setUserId(String userId);

    String getUserId();

    void setUsername(String username);

    String getUsername();
}
