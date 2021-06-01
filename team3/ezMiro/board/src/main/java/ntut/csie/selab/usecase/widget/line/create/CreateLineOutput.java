package ntut.csie.selab.usecase.widget.line.create;

import ntut.csie.selab.entity.model.widget.Coordinate;

public class CreateLineOutput {
    private String lineId;
    private String boardId;
    private Coordinate coordinate;

    public String getLineId() {
        return lineId;
    }

    public String getBoardId() {
        return boardId;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
