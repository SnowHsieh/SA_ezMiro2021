package ntut.csie.sslab.kanban.entity.model.board2;


public class BoardMemberBuilder {
	private String boardId;
	private String userId;
	private BoardMemberType memberType;
	
	public static BoardMemberBuilder newInstance() {
		return new BoardMemberBuilder();
	}
	
	public BoardMemberBuilder boardId(String boardId) {
		this.boardId = boardId;
		return this;
	}
	
	public BoardMemberBuilder userId(String userId) {
		this.userId = userId;
		return this;
	}

	public BoardMemberBuilder memberType(BoardMemberType memberType) {
		this.memberType = memberType;
		return this;
	}

	public BoardMember build() {
		BoardMember boardMember = new BoardMember(memberType ,boardId, userId);
		return boardMember;
	}
}
