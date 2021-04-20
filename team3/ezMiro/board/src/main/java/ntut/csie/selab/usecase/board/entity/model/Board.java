package ntut.csie.selab.usecase.board.entity.model;

public class Board {
    private String teamId;
    private String boardName;

    public Board(String teamId, String boardName) {
        this.teamId = teamId;
        this.boardName = boardName;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getBoardName() {
        return boardName;
    }
}
