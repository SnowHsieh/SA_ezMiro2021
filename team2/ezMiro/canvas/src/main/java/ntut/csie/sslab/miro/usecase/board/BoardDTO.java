package ntut.csie.sslab.miro.usecase.board;

import ntut.csie.sslab.miro.entity.model.board.BoardChannel;
import ntut.csie.sslab.miro.entity.model.board.CommittedFigure;
import java.util.Map;

public class BoardDTO {
    private String teamId;
    private String boardName;
    private Map<String, CommittedFigure> committedFigures;
    private BoardChannel boardChannel;

    public BoardDTO(String teamId, String boardName, Map<String, CommittedFigure> committedFigures, BoardChannel boardChannel) {
        this.teamId = teamId;
        this.boardName = boardName;
        this.committedFigures = committedFigures;
        this.boardChannel = boardChannel;
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

    public Map<String, CommittedFigure> getCommittedFigures() {
        return committedFigures;
    }

    public void setCommittedFigures(Map<String, CommittedFigure> committedFigures) {
        this.committedFigures = committedFigures;
    }

    public BoardChannel getBoardChannel() {
        return boardChannel;
    }

    public void setBoardChannel(BoardChannel boardChannel) {
        this.boardChannel = boardChannel;
    }
}
