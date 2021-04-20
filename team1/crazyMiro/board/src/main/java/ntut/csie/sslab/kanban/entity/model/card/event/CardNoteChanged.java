package ntut.csie.sslab.kanban.entity.model.card.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class CardNoteChanged extends DomainEvent{
	
	private final String cardId;
	private final String oldNote;
	private final String newNote;
	private final String userId;
	private final String username;
	private final String boardId;
	
	public CardNoteChanged(String cardId, String oldNote, String newNote, String userId, String username, String boardId) {
		super(DateProvider.now());
		this.cardId = cardId;
		this.oldNote = oldNote;
		this.newNote = newNote;
		this.userId = userId;
		this.username = username;
		this.boardId = boardId;
	}
	
	public String cardId() {
		return cardId;
	}

	public String oldNote() {
		return oldNote;
	}

	public String newNote() {
		return newNote;
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
