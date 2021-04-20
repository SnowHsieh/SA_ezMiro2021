package ntut.csie.sslab.kanban.entity.model.workflow.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class StageMoved extends DomainEvent {

	private final String workflowId;
	private final String stageId;
	private final int oldOrder;
	private final int newOrder;
	private final String stageName;
	private final String userId;
	private final String userName;
	private final String boardId;

	public StageMoved(String workflowId, String stageId, int oldOrder, int newOrder, String stageName, String userId, String userName, String boardId) {
		super(DateProvider.now());
		this.workflowId = workflowId;
		this.stageId = stageId;
		this.oldOrder = oldOrder;
		this.newOrder = newOrder;
		this.stageName = stageName;
		this.userId = userId;
		this.userName = userName;
		this.boardId = boardId;
	}
	
	public String workflowId() {
		return workflowId;
	}

	public String stageId() {
		return stageId;
	}

	public int oldOrder() { return oldOrder; }

	public int newOrder() { return newOrder; }

	public String stageName() {
		return stageName;
	}

	public String userId() {
		return userId;
	}

	public String userName() {
		return userName;
	}

	public String boardId() {
		return boardId;
	}
}
