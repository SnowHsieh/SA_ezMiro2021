package ntut.csie.sslab.kanban.entity.model.card.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class CardEstimateChanged extends DomainEvent {
	
	private final String cardId;
	private final String oldEstimate;
	private final String newEstimate;
	private final String userId;
	private final String username;
	private final String boardId;
	
	public CardEstimateChanged(String cardId, String oldEstimate, String newEstimate, String userId, String username, String boardId) {
		super(DateProvider.now());
		this.cardId = cardId;
		this.oldEstimate = oldEstimate;
		this.newEstimate = newEstimate;
		this.userId = userId;
		this.username = username;
		this.boardId = boardId;
	}


	public String cardId() {
		return cardId;
	}

	public String oldEstimate() {
		return oldEstimate;
	}

	public String newEstimate() {
		return newEstimate;
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
