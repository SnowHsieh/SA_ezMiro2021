package ntut.csie.sslab.kanban.entity.model.board2.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class BoardDeleted extends DomainEvent {
	
	private final String teamId;
	private final String boardId;
	private final String userId;
	private final String username;

	public BoardDeleted(String teamId, String boardId, String userId, String username) {
		super(DateProvider.now());
		this.teamId = teamId;
		this.boardId = boardId;
		this.userId = userId;
		this.username = username;
	}
	
	public String teamId() {
		return teamId;
	}

	public String boardId() {
		return boardId;
	}

	public String userId() {
		return userId;
	}

	public String username() {
		return username;
	}
}
