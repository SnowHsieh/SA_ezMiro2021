package ntut.csie.sslab.kanban.usecase.card.create;

import ntut.csie.sslab.ddd.usecase.Input;

public interface CreateCardInput extends Input{
	public String getWorkflowId();

	public void setWorkflowId(String workflowId);

	public String getLaneId();

	public void setLaneId(String laneId);

	public String getUserId();

	public void setUserId(String userId);

	public String getDescription();
	
	public void setDescription(String description);

	public String getEstimate();
	
	public void setEstimate(String estimate);
	
	public String getNote();
	
	public void setNote(String note);
	
	public String getDeadline();
	
	public void setDeadline(String deadLine);

	public void setType(String type);

	public String getType();

    public void setUsername(String username);

    public String getUsername();

    public void setBoardId(String boardId);

    public String getBoardId();
}
