package ntut.csie.selab.usecase.widget.stickynote.create;


import ntut.csie.selab.entity.model.widget.Coordinate;

public class CreateStickyNoteInput {
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
