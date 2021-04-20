package ntut.csie.sslab.kanban.entity.model.card.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class CardDescriptionChanged extends DomainEvent {
	
	private final String cardId;
	private final String oldDescription;
	private final String newDescription;
	private final String userId;
	private final String username;
	private final String boardId;
	
	public CardDescriptionChanged(String cardId, String oldDescription, String newDescription, String  userId, String username, String boardId) {
		super(DateProvider.now());
		this.cardId = cardId;
		this.oldDescription = oldDescription;
		this.newDescription = newDescription;
		this.userId = userId;
		this.username = username;
		this.boardId = boardId;
	}
	
	
	public String cardId() {
		return cardId;
	}
	
	public String oldDescription() {
		return oldDescription;
	}
	
	public String newDescription() {
		return newDescription;
	}

	public String userId() { return userId; }

	public String username() { return username; }

	public String boardId() { return boardId; }
}
