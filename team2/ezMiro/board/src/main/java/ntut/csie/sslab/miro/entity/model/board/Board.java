package ntut.csie.sslab.miro.entity.model.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.miro.entity.model.board.event.*;
import ntut.csie.sslab.miro.entity.model.board.event.BoardEntered;

public class Board extends AggregateRoot<String> {
	private String name;
	private String teamId;
	private List<BoardMember> boardMembers;
	private List<CommittedWorkflow> committedWorkflows;
	private List<BoardSession> boardSessions;


	public Board(String userId, String teamId, String boardId, String name) {
		super(boardId);
		this.name = name;
		this.teamId = teamId;

		boardMembers = new ArrayList<>();
		committedWorkflows = new ArrayList<>();
		boardSessions = new ArrayList<>();

		addDomainEvent(new BoardCreated(teamId, boardId, name, userId));
	}

	public void markAsRemoved(String userId, String username) {
		addDomainEvent(new BoardDeleted(teamId, getBoardId(), userId, username));
	}

	public void renameName(String newName) {
		if(!(name.equals(newName))){
			String oldName = name;
			setName(newName);
			addDomainEvent(new BoardRenamed(teamId, getBoardId(), oldName, newName));
		}
	}

	public void addWorkflow(String workflowId) {
		int order = 0;
		if(committedWorkflows.size() > 0) {
			order = committedWorkflows.get(committedWorkflows.size()-1).getOrder() + 1;
		}
		CommittedWorkflow committedWorkflow = new CommittedWorkflow(getBoardId(), workflowId, order);
		committedWorkflows.add(committedWorkflow);
	}

	public void commitWorkflow(String workflowId) {
		addWorkflow(workflowId);
		addDomainEvent(new WorkflowCommitted(getBoardId(), workflowId));
	}

	public void removeWorkflow(String workflowId) {
		for(int i = 0; i < committedWorkflows.size(); i++){
			if(committedWorkflows.get(i).getWorkflowId().equals(workflowId)) {
				committedWorkflows.remove(i);
			}
		}
	}

	public void uncommitworkflow(String workflowId) {
		removeWorkflow(workflowId);
		addDomainEvent(new WorkflowUncommitted(getBoardId(), workflowId));
	}

	public void becameBoardMember(BoardMemberType memberType, String userId) {
		addBoardMember(memberType, userId);
		addDomainEvent(new BoardMemberAdded(userId, getBoardId(), memberType.toString()));
	}

	public void addBoardMember(BoardMemberType memberType, String userId) {
		BoardMember boardMember = BoardMemberBuilder.newInstance()
				.memberType(memberType)
				.boardId(getBoardId())
				.userId(userId)
				.build();
		boardMembers.add(boardMember);
	}


	public void removeBoardMember(String userId) {
		for(BoardMember boardMember : boardMembers) {
			if(boardMember.getUserId().equals(userId)) {
				boardMembers.remove(boardMember);
				addDomainEvent(new BoardMemberRemoved(userId, getBoardId()));
				break;
			}
		}
	}


	public boolean isCreator(String userId) {
		for(BoardMember boardMember : boardMembers) {
			if(boardMember.getMemberType().equals(BoardMemberType.Manager)
					&& boardMember.getUserId().equals(userId)) {
				return true;
			}
		}
		return false;
	}

	public boolean isInvitedMember(String userId) {
		for(BoardMember boardMember : boardMembers) {
			if(boardMember.getMemberType().equals(BoardMemberType.Member)
				&& boardMember.getUserId().equals(userId)) {
				return true;
			}
		}
		return false;
	}
	


	public String acceptUserEntry(String userId) {
		BoardSession boardSession = new BoardSession(UUID.randomUUID().toString(), userId, getBoardId());
		boardSessions.add(boardSession);

		addDomainEvent(new BoardEntered(userId, getBoardId(), boardSession.getBoardSessionId()));
		return boardSession.getBoardSessionId();
	}

	public String acceptUserLeaving(String boardSessionId) {
		String userId = null;
		for(BoardSession boardSession: boardSessions){
			if(boardSession.getBoardSessionId().equals(boardSessionId)){
				userId = boardSession.getUserId();
				boardSessions.remove(boardSession);
				break;
			}
		}
		addDomainEvent(new BoardLeft(userId,getBoardId(), boardSessionId));
		return boardSessionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBoardId() {
		return getId();
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public BoardMember getBoardMember(String userId){

		for(BoardMember each : boardMembers){
			if(each.getUserId().equalsIgnoreCase(userId)){
				return each;
			}
		}
		return null;
	}

	public List<BoardMember> getBoardMembers() {
		return boardMembers;
	}

	public void setBoardMembers(List<BoardMember> boardMembers) {
		this.boardMembers = boardMembers;
	}

	public List<CommittedWorkflow> getCommittedWorkflows() {
		return committedWorkflows;
	}

	public void setCommittedWorkflows(List<CommittedWorkflow> committedWorkflows) {
		this.committedWorkflows = committedWorkflows;
	}

	public List<BoardSession> getBoardSessions() {
		return boardSessions;
	}

	public void setBoardSessions(List<BoardSession> boardSessions) {
		this.boardSessions = boardSessions;
	}


	public void addBoardSession(BoardSession boardSession) {
		boardSessions.add(boardSession);
	}

    public Optional<BoardMember> getBoardMemberById(String userId) {
		return boardMembers.stream().
				filter( x -> x.getUserId().equalsIgnoreCase(userId)).
				findFirst();
    }
}
