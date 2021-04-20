package ntut.csie.sslab.kanban.entity.model.workflow.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class SwimLaneCreated extends DomainEvent {

	private final String workflowId;
	private final String parentId;
	private final String swimLaneId;
	private final String name;
	private final String type;
	private final int wipLimit;
	private final String userId;
	private final String username;
	private final String boardId;

	public SwimLaneCreated(String workflowId, String parentId, String swimLaneId, String name, String type, int wipLimit, String userId, String username, String boardId) {
		super(DateProvider.now());
		this.workflowId = workflowId;
		this.parentId = parentId;
		this.swimLaneId = swimLaneId;
		this.name = name;
		this.type =type;
		this.wipLimit = wipLimit;
		this.userId = userId;
		this.username = username;
		this.boardId = boardId;
	}
	
	public String workflowId() { return workflowId;}

	public String parentId() {return parentId;}

	public String swimLaneId() {
		return swimLaneId;
	}
	
	public String name() {
		return name;
	}

	public String type() { return type; }

	public int wipLimit() { return wipLimit; }

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
