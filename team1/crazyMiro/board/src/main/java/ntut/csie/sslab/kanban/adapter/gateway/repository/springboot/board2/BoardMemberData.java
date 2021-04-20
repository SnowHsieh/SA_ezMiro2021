package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.board2;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="board_member")
public class BoardMemberData {

//	@Column(name="board_id", nullable = false)
//	private String boardId;
//
//	@Column(name="user_id", nullable = false)
//	private String userId;
	@EmbeddedId
	private BoardMemberDataId boardMemberDataId;

	public BoardMemberData(BoardMemberDataId boardMemberDataId, String memberType) {
		this.boardMemberDataId = boardMemberDataId;
		this.memberType = memberType;
	}

	public BoardMemberData() {
	}

	@Column(name="member_type", nullable = false)
	private String memberType;

	public String getBoardId() {
		return boardMemberDataId.getBoardId();
	}

	public void setBoardId(String boardId) {
		boardMemberDataId.setBoardId(boardId);
	}

	public String getUserId() {
		return boardMemberDataId.getUserId();
	}

	public void setUserId(String userId) {
		boardMemberDataId.setUserId(userId);
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 79 * hash + Objects.hashCode(this.boardMemberDataId.getUserId());
		hash = 79 * hash + Objects.hashCode(this.boardMemberDataId.getBoardId());
		hash = 79 * hash + Objects.hashCode(this.memberType);
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
		final BoardMemberData other = (BoardMemberData) obj;
		if (!Objects.equals(this.boardMemberDataId, other.boardMemberDataId)) {
			return false;
		}
		if (!Objects.equals(this.memberType, other.memberType)) {
			return false;
		}
		return true;
	}
}
