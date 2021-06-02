package ntut.csie.selab.usecase.widget.line.create;

import ntut.csie.selab.entity.model.widget.Coordinate;

public class CreateLineInput {
    private String boardId;
    private Coordinate coordinate;

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getBoardId() {
        return boardId;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
