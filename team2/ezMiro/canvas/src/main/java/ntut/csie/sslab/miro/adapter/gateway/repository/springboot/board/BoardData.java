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
	private String boardName;

	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name="board_id_fk1")
	@OrderBy("z_order")
	private Set<CommittedFigureData> committedFigureDatas;

	@Column(name="board_channel")
	private String boardChannel;

	public BoardData(){
		committedFigureDatas = new HashSet<>();
	}

	public BoardData(String teamId, String boardId, String name, String boardChannel) {
		this();
		this.boardId = boardId;
		this.teamId = teamId;
		this.boardName = name;
		this.boardChannel = boardChannel;
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

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public String getBoardChannel() {
		return boardChannel;
	}

	public void setBoardChannel(String boardChannel) {
		this.boardChannel = boardChannel;
	}

	public List<CommittedFigureData> getCommittedFigureDatas() {
		return new ArrayList<>(committedFigureDatas);
	}

	public void setCommittedFigureDatas(List<CommittedFigureData> committedFigureDatas) {
		this.committedFigureDatas = new HashSet<>(committedFigureDatas);
	}
}