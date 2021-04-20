package ntut.csie.sslab.kanban.entity.model.card.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class CardCreated extends DomainEvent {
	
	private final String workflowId;
	private final String laneId;
	private final String cardId;
	private final String description;
	private final String estimate;
	private final String notes;
	private final String deadline;
	private final String type;
	private final String userId;
	private final String username;
	private final String boardId;

	public CardCreated(String workflowId, String laneId, String cardId, String description, String estimate, String notes, String deadline, String type, String userId, String username, String boardId) {
		super(DateProvider.now());
		this.workflowId = workflowId;
		this.laneId = laneId;
		this.cardId = cardId;
		this.description = description;
		this.estimate = estimate;
		this.notes = notes;
		this.deadline = deadline;
		this.type = type;
		this.userId = userId;
		this.username = username;
		this.boardId = boardId;
	}

	public String userId() {
		return userId;
	}

	public String username() {
		return username;
	}

	public String boardId() {
		return boardId;
	}

	public String workflowId() {
		return workflowId;
	}

	public String laneId() {
		return laneId;
	}

	public String cardId() {
		return cardId;
	}

	public String description() {
		return description;
	}

	public String estimate() {
		return estimate;
	}

	public String notes() {
		return notes;
	}

	public String deadline() {
		return deadline;
	}

	public String type() {
		return type;
	}
}
