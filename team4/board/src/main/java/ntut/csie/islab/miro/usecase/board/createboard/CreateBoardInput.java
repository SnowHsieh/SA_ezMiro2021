package ntut.csie.islab.miro.usecase.board;

import java.util.UUID;

public class CreateBoardInput {
    private UUID teamId;
    private String boardName;

    public UUID getTeamId() {
        return teamId;
    }

    public void setTeamId(UUID teamId) {
        this.teamId = teamId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }
}
