package ntut.csie.sslab.kanban.usecase.card.delete;

import ntut.csie.sslab.ddd.usecase.Input;

public interface DeleteCardInput extends Input{
	public String getCardId();
	
	public void setCardId(String cardId);
	
	public String getWorkflowId();
	
	public void setWorkflowId(String workflowId);

	public String getLaneId();

	public void setLaneId(String LaneId);

	public String getUserId();

	public void setUserId(String userId);

	public String getUsername();

	public void setUsername(String username);

	public String getBoardId();

	public void setBoardId(String boardId);
}
