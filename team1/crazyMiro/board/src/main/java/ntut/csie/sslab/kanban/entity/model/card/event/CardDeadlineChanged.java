package ntut.csie.sslab.kanban.entity.model.card.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class CardDeadlineChanged extends DomainEvent{
	
	private final String cardId;
	private final String oldDeadline;
	private final String newDeadline;
	private final String userId;
	private final String username;
	private final String boardId;
	
	public CardDeadlineChanged(String cardId, String oldDeadline, String newDeadline, String userId, String username, String boardId) {
		super(DateProvider.now());
		this.cardId = cardId;
		this.oldDeadline = oldDeadline;
		this.newDeadline = newDeadline;
		this.userId = userId;
		this.username = username;
		this.boardId = boardId;
	}
	
	public String cardId() {
		return cardId;
	}

	public String oldDeadline() {
		return oldDeadline;
	}

	public String newDeadline() {
		return newDeadline;
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
}
