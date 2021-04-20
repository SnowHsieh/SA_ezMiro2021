package ntut.csie.sslab.kanban.entity.model.board2.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class BoardCreated extends DomainEvent {

	private final String teamId;
	private final String boardId;
	private final String name;
	private final String userId;

	public BoardCreated(String teamId, String boardId, String name, String userId) {
		super(DateProvider.now());
		this.teamId = teamId;
		this.boardId = boardId;
		this.name = name;
		this.userId = userId;
	}
	
	public String teamId() { return teamId; }

	public String boardId() {
		return boardId;
	}

	public String name() {return name;}

	public String userId() { return userId; }
}
