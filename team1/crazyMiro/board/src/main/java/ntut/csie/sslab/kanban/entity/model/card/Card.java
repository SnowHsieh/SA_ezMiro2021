package ntut.csie.sslab.kanban.entity.model.card;

import java.util.*;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.kanban.entity.model.card.event.CardBlocked;
import ntut.csie.sslab.kanban.entity.model.card.event.CardUnblocked;
import ntut.csie.sslab.kanban.entity.model.card.event.*;
import ntut.csie.sslab.kanban.entity.model.workflow.event.CardMoved;

public class Card extends AggregateRoot<String> {
	private String userId;
	private String boardId;
	private String workflowId;
	private String laneId;
	private String description;
	private String estimate;
	private String notes;
	private String deadline;
	private CardType type;

	public Card(String userId, String username, String boardId, String workflowId, String laneId, String cardId, String description,
				String estimate, String notes, String deadline, CardType type) {
		super(cardId);
		this.boardId = boardId;
		this.workflowId = workflowId;
		this.laneId = laneId;
		this.userId = userId;
		this.description = description;
		this.type = type;
		this.estimate = estimate;
		this.notes = notes;
		this.deadline = deadline;
		addDomainEvent(new CardCreated(workflowId, laneId, cardId, description, estimate, notes, deadline, type.toString(), userId, username, boardId));
	}
	
	public void changeDescription(String newDescription, String userId, String username, String boardId) {

		if(!newDescription.isEmpty() && !description.equals(newDescription)) {
			String originalDescription = description;
			this.setDescription(newDescription);
			addDomainEvent(new CardDescriptionChanged(getId(), originalDescription, newDescription, userId, username, boardId));
		}
	}

	public void changeDeadline(String newDeadline, String userId, String username, String boardId) {
		if(!deadline.equals(newDeadline)) {
			String oldDeadline = deadline;
			this.setDeadline(newDeadline);
			addDomainEvent(new CardDeadlineChanged(
					getId(), oldDeadline, newDeadline, userId, username, boardId));
		}
	}
	
	public void changeEstimate(String newEstimate, String userId, String username, String boardId) {
		if(!estimate.equals(newEstimate)) {
			String oldEstimate = estimate;
			this.setEstimate(newEstimate);
			addDomainEvent(new CardEstimateChanged(
					getId(),
					oldEstimate,
					newEstimate,
					userId,
					username,
					boardId));
		}
	}
	
	public void changeNotes(String newNotes, String userId, String username, String boardId) {
		if(!notes.equals(newNotes)) {
			String oldNotes = notes;
			this.setNotes(newNotes);
			addDomainEvent(new CardNoteChanged(
					getId(), oldNotes, newNotes, userId, username, boardId));
		}
	}

	public void changeType(CardType newType, String userId, String username, String boardId) {
		if(!type.equals(newType)) {
			CardType oldType = type;
			this.setType(newType);
			addDomainEvent(new CardTypeChanged(getId(), oldType.toString(), newType.toString(), userId, username, boardId));
		}
	}
	
	public void markAsRemoved(String workflowId, String laneId, String userId, String username, String boardId) {
		addDomainEvent(new CardDeleted(
				workflowId,
				laneId,
				getId(),
				getDescription(),
				userId,
				username,
				boardId));
	}

	public String getCardId() {
		return getId();
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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

	public void move(String boardId, String workflowId, String newLaneId, String userId, String username, int newOrder) {
		String oldLaneId = laneId;
		setLaneId(newLaneId);
		addDomainEvent(new CardMoved(workflowId, getId(), oldLaneId, newLaneId, newOrder, userId, username, boardId));
	}

}
