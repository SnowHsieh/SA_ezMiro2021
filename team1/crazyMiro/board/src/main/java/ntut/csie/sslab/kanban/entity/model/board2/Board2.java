package ntut.csie.sslab.kanban.entity.model.board2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.kanban.entity.model.board2.event.*;
import ntut.csie.sslab.kanban.entity.model.board2.event.BoardEntered;

import static java.lang.String.format;
import static ntut.csie.sslab.ddd.model.common.Contract.*;

public class Board2 extends AggregateRoot<String> {
	private String name;
	private String teamId;
	private List<BoardMember> boardMembers;
	private List<CommittedWorkflow> committedWorkflows;
	private List<BoardSession> boardSessions;


	public Board2(String teamId, String boardId, String name, String userId) {
		super(boardId);

		requireNotNull("Team id", teamId);
		requireNotNull("board id", boardId);
		requireNotNull("Board name", name);
		requireNotNull("User id", userId);

		this.name = name;
		this.teamId = teamId;

		boardMembers = new ArrayList<>();
		committedWorkflows = new ArrayList<>();
		boardSessions = new ArrayList<>();

		addDomainEvent(new BoardCreated(teamId, boardId, name, userId));
	}

	public void markAsRemoved(String userId, String username) {
		requireNotNull("User id", userId);

		isDeleted = true;
		addDomainEvent(new BoardDeleted(teamId, getBoardId(), userId, username));

		ensure("Board is marked as deleted", isDeleted);
	}

	public void renameName(String newName) {
		requireNotNull("Board name", newName);

		if((name.equals(newName)))
			return;

		String oldName = name;
		this.name = newName;
		addDomainEvent(new BoardRenamed(teamId, getBoardId(), oldName, newName));

		ensure(format("Board name is '%s'", newName), () -> getName().equals(newName));
	}


	public Optional<CommittedWorkflow> getCommittedWorkflow(String workflowId) {
		requireNotNull("Workflow id", workflowId);

		return committedWorkflows
				.stream()
				.filter(x->x.getWorkflowId().equals(workflowId))
				.findFirst();
	}

	public void commitWorkflow(String workflowId) {
		requireNotNull("Workflow id", workflowId);

		addWorkflow(workflowId);
		addDomainEvent(new WorkflowCommitted(getBoardId(), workflowId));

		ensure(format("Workflow '%s' is committed", workflowId), () -> getCommittedWorkflow(workflowId).isPresent());
	}


	public void uncommitworkflow(String workflowId) {
		requireNotNull("Workflow id", workflowId);

		removeWorkflow(workflowId);
		addDomainEvent(new WorkflowUncommitted(getBoardId(), workflowId));

		ensure(format("Workflow '%s' is uncommitted", workflowId), () -> !getCommittedWorkflow(workflowId).isPresent());
	}

	public void becameBoardMember(BoardMemberType memberType, String userId) {
		requireNotNull("Board member type", memberType);
		requireNotNull("User id", userId);

		addBoardMember(memberType, userId);
		addDomainEvent(new BoardMemberAdded(userId, getBoardId(), memberType.toString()));

		ensure(format("User '%s' becomes a board member", userId), () -> getBoardMember(userId).isPresent());
		ensure(format("User role is '%s'", memberType), () -> getBoardMember(userId).get().getMemberType().equals(memberType));
	}

	private void addBoardMember(BoardMemberType memberType, String userId) {
		BoardMember boardMember = BoardMemberBuilder.newInstance()
				.memberType(memberType)
				.boardId(getBoardId())
				.userId(userId)
				.build();
		boardMembers.add(boardMember);
	}


	public void removeBoardMember(String userId) {
		requireNotNull("User id", userId);

		for(BoardMember boardMember : boardMembers) {
			if(boardMember.getUserId().equals(userId)) {
				boardMembers.remove(boardMember);
				addDomainEvent(new BoardMemberRemoved(userId, getBoardId()));
				break;
			}
		}

		ensure(format("Board member '%s' is removed", userId), () -> !getBoardMember(userId).isPresent());
	}

	public String acceptUserEntry(String userId) {
		requireNotNull("User id", userId);
		require(format("User '%s' is a board member", userId), () -> isBoardMember(userId));

		BoardSession boardSession = new BoardSession(UUID.randomUUID().toString(), userId, getBoardId());
		boardSessions.add(boardSession);
		addDomainEvent(new BoardEntered(userId, getBoardId(), boardSession.getBoardSessionId()));

		ensure(format("Board session '%s' is accepted", boardSession.getBoardSessionId()), () -> getBoardSession(boardSession.getBoardSessionId()).isPresent());

		return boardSession.getBoardSessionId();
	}

	public String acceptUserLeaving(String boardSessionId) {
		requireNotNull("Board session id", boardSessionId);

		String userId = null;
		for(BoardSession boardSession: boardSessions){
			if(boardSession.getBoardSessionId().equals(boardSessionId)){
				userId = boardSession.getUserId();
				boardSessions.remove(boardSession);
				break;
			}
		}
		addDomainEvent(new BoardLeft(userId,getBoardId(), boardSessionId));

		ensure("User left board", ()-> !boardSessions.stream().anyMatch(x-> x.getBoardSessionId().equals(boardSessionId)));
		return boardSessionId;
	}

	public String getName() {
		return name;
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


	public Optional<BoardMember> getBoardMember(String userId){
		requireNotNull("User id", userId);

		for(BoardMember each : boardMembers){
			if(each.getUserId().equalsIgnoreCase(userId)){
				return Optional.of(each);
			}
		}
		return Optional.empty();
	}

	public boolean isBoardMember(String userId){
		requireNotNull("User id", userId);

		return getBoardMember(userId).isPresent();
	}

	public Optional<BoardSession> getBoardSession(String sessionId){
		for(BoardSession each : boardSessions){
			if(each.getBoardSessionId().equals(sessionId)){
				return Optional.of(each);
			}
		}
		return Optional.empty();
	}

	public List<BoardMember> getBoardMembers() {
		return boardMembers;
	}

	public List<CommittedWorkflow> getCommittedWorkflows() {
		return committedWorkflows;
	}

	public List<BoardSession> getBoardSessions() {
		return boardSessions;
	}

    public Optional<BoardMember> getBoardMemberById(String userId) {
		return boardMembers.stream().
				filter( x -> x.getUserId().equalsIgnoreCase(userId)).
				findFirst();
    }

	private void addWorkflow(String workflowId) {
		requireNotNull("Workflow id", workflowId);

		int order = 0;
		if(committedWorkflows.size() > 0) {
			order = committedWorkflows.get(committedWorkflows.size()-1).getOrder() + 1;
		}
		CommittedWorkflow committedWorkflow = new CommittedWorkflow(getBoardId(), workflowId, order);
		committedWorkflows.add(committedWorkflow);

		ensure(format("Workflow '%s' is committed", workflowId), () -> getCommittedWorkflow(workflowId).isPresent());
	}

	private void removeWorkflow(String workflowId) {

		for(int i = 0; i < committedWorkflows.size(); i++){
			if(committedWorkflows.get(i).getWorkflowId().equals(workflowId)) {
				committedWorkflows.remove(i);
			}
		}
	}

}
