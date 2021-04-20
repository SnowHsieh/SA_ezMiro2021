package ntut.csie.sslab.kanban.entity.model.card;

import java.util.UUID;

public class CardBuilder {
	private String workflowId;
	private String laneId;
	private String userId;
	private String description;
	private String estimate;
	private String notes;
	private String deadline;
	private CardType type;
	private String username;
	private String boardId;
	
	public static CardBuilder newInstance() {
		return new CardBuilder();
	}

	public CardBuilder workflowId(String workflowId) {
		this.workflowId = workflowId;
		return this;
	}

	public CardBuilder laneId(String laneId) {
		this.laneId = laneId;
		return this;
	}

	public CardBuilder userId(String userId) {
		this.userId = userId;
		return this;
	}
	
	public CardBuilder description(String description) {
		this.description = description;
		return this;
	}
	
	public CardBuilder estimate(String estimate) {
		this.estimate = estimate;
		return this;
	}
	
	public CardBuilder notes(String notes) {
		this.notes = notes;
		return this;
	}
	
	public CardBuilder deadline(String deadline) {
		this.deadline = deadline;
		return this;
	}

	public CardBuilder type(CardType type) {
		this.type = type;
		return this;
	}

	public CardBuilder username(String username) {
		this.username = username;
		return this;
	}

	public CardBuilder boardId(String boardId) {
		this.boardId = boardId;
		return this;
	}

	public Card build() {
		String cardId = UUID.randomUUID().toString();
		Card card = new Card(userId, username, boardId, workflowId, laneId, cardId, description, estimate, notes, deadline, type);
		return card;
	}
}
