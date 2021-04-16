package ntut.csie.sslab.miro.entity.model.board;

public class BoardMember{
	private String boardId;
	private String userId;
	private BoardMemberType memberType;

	BoardMember(BoardMemberType memberType, String boardId, String userId) {
		this.memberType = memberType;
		this.boardId = boardId;
		this.userId = userId;
	}


	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BoardMemberType getMemberType() {
		return memberType;
	}

	public void setMemberType(BoardMemberType memberType) {
		this.memberType = memberType;
	}
}
