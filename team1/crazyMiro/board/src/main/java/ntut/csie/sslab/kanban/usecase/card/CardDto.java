package ntut.csie.sslab.kanban.usecase.card;

import ntut.csie.sslab.kanban.entity.model.card.CardType;

import java.util.List;
import java.util.Set;

public class CardDto {
	private String cardId;
	private String boardId;
	private String workflowId;
	private String laneId;
	private String description;
	private String userId;
	private String estimate;
	private String notes;
	private String deadline;
	private CardType type;

	public String getCardId() {
		return cardId;
	}
	
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public String getLaneId() {
		return laneId;
	}
	
	public void setLaneId(String laneId) {
		this.laneId = laneId;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getEstimate() {
		return estimate;
	}
	
	public void setEstimate(String estimate) {
		this.estimate = estimate;
	}

	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getDeadline() {
		return deadline;
	}
	
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public CardType getType() {
		return type;
	}

	public void setType(CardType type) {
		this.type = type;
	}

}
