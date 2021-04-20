package ntut.csie.sslab.kanban.usecase.board2;


import java.util.List;

public class BoardDto {
	private String boardId;
	private String projectId;
	private String title;
	private List<BoardMemberDto> boardMembers;
	private List<CommittedWorkflowDto> committedWorkflows;
	private int order;

	public String getBoardId() {
		return boardId;
	}
	
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public List<BoardMemberDto> getBoardMembers() {
		return boardMembers;
	}

	public void setBoardMembers(List<BoardMemberDto> boardMembers) {
		this.boardMembers = boardMembers;
	}

	public List<CommittedWorkflowDto> getCommittedWorkflows() {
		return committedWorkflows;
	}

	public void setCommittedWorkflows(List<CommittedWorkflowDto> committedWorkflows) {
		this.committedWorkflows = committedWorkflows;
	}

	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
}
