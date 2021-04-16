package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="board")
public class BoardData {

	@Id
	@Column(name="board_id")
	private String boardId;

	@Column(name="team_id", nullable = false)
	private String teamId;

	@Column(name="board_name")
	private String name;

	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	@JoinColumn(name="board_id_fk1")
	@OrderBy("order")
	private Set<CommittedWorkflowData> committedWorkflowDatas;

	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	@JoinColumn(name="board_id_fk2")
	private Set<BoardMemberData> boardMemberDatas;

	public BoardData(){
		committedWorkflowDatas = new HashSet<>();
		boardMemberDatas = new HashSet<>();
	}

	public BoardData(String teamId, String boardId, String name) {
		this();
		this.boardId = boardId;
		this.teamId = teamId;
		this.name = name;
	}



	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CommittedWorkflowData> getCommittedWorkflowDatas() {
		return new ArrayList<>(committedWorkflowDatas);
	}

	public void setCommittedWorkflowDatas(List<CommittedWorkflowData> committedWorkflowData) {
		this.committedWorkflowDatas = new HashSet<>(committedWorkflowData);
	}

	public void addCommittedWorkflowData(CommittedWorkflowData workflowData){
		this.committedWorkflowDatas.add(workflowData);
	}

	public List<BoardMemberData> getBoardMemberDatas() {
		return new ArrayList<>(boardMemberDatas);
	}

	public void setBoardMemberDatas(List<BoardMemberData> boardMemberDatas) {
		this.boardMemberDatas = new HashSet<>(boardMemberDatas);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 79 * hash + Objects.hashCode(this.boardId);
		hash = 79 * hash + Objects.hashCode(this.name);
		hash = 79 * hash + Objects.hashCode(this.teamId);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final BoardData other = (BoardData) obj;
		if (!Objects.equals(this.teamId, other.teamId)) {
			return false;
		}
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		return Objects.equals(this.boardId, other.boardId);
	}

}
