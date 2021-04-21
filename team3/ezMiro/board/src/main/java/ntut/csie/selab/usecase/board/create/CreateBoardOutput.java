package ntut.csie.selab.usecase.board.create;

public class CreateBoardOutput {
    private String boardId;
    private String teamId;
    private String boardName;
    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String id) {
        this.boardId = id;
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
}
