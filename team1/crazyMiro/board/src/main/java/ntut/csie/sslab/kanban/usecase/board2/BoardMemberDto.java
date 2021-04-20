package ntut.csie.sslab.kanban.usecase.board2;

import ntut.csie.sslab.kanban.entity.model.board2.BoardMemberType;

public class BoardMemberDto {
    private String boardId;
    private String userId;
    private BoardMemberType memberType;

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
