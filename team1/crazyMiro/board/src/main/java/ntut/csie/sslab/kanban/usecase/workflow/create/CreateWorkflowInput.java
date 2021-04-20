package ntut.csie.sslab.kanban.usecase.workflow.create;

import ntut.csie.sslab.ddd.usecase.Input;

public interface CreateWorkflowInput extends Input {

    public void setWorkflowName(String title);

    public String getWorkflowName();

    public void setBoardId(String boardId);

    public String getBoardId();

    public void setUsername(String username);

    public String getUsername();

    public void setUserId(String userId);

    public String getUserId();
}
