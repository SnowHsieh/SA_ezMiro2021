package ntut.csie.islab.miro.usecase.figure.line.changepath;

import ntut.csie.islab.miro.entity.model.Position;

import java.util.List;
import java.util.UUID;

public class ChangeLinePathInput {
    private UUID boardId;
    private UUID figureId;
    private List<Position> positionList;

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    public UUID getFigureId() {
        return figureId;
    }

    public void setFigureId(UUID figureId) {
        this.figureId = figureId;
    }

    public List<Position> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<Position> positionList) {
        this.positionList = positionList;
    }
}
