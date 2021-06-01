package ntut.csie.selab.usecase.widget.stickynote.create;


import ntut.csie.selab.entity.model.widget.Coordinate;

public class CreateStickyNoteOutput {
    private String stickyNoteId;
    private String boardId;
    private Coordinate coordinate;

    public String getStickyNoteId() {
        return stickyNoteId;
    }

    public String getBoardId() {
        return boardId;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setStickyNoteId(String stickyNoteId) {
        this.stickyNoteId = stickyNoteId;
    }
}
