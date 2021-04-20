package ntut.csie.sslab.kanban.usecase.lane.swimLane.create;

import ntut.csie.sslab.ddd.usecase.Input;

public interface CreateSwimLaneInput extends Input {
    public void setWorkflowId(String workflowId);

    public String getWorkflowId();

    public void setParentId(String parentId);

    public String getParentId();

    public void setName(String title);

    public String getName();

    public void setWipLimit(int wipLimit);

    public int getWipLimit();

    public void setLaneType(String laneType);

    public String getLaneType();

    public void setUserId(String userId);

    public String getUserId();

    public void setUsername(String username);

    public String getUsername();

    public void setBoardId(String boardId);

    public String getBoardId();

}
