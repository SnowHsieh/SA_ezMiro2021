package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.card;


import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="card")
public class CardData {

	@Id
	@Column(name="card_id")
	private String cardId;

	@Column(name="board_id", nullable = false)
	private String boardId;

	@Column(name="workflow_id", nullable = false)
	private String workflowId;

	@Column(name="lane_id", nullable = false)
	private String laneId;

	@Column(name="description")
	private String description;

	@Column(name="user_id")
	private String userId;

	@Column(name="estimate")
	private String estimate;

	@Column(name="notes")
	private String notes;

	@Column(name="deadline")
	private String deadline;

	@Column(name="type")
	private String type;

	public CardData(){
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String workItemId) {
		this.cardId = workItemId;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
