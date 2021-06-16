package ntut.csie.selab.usecase.widget.stickynote.create;


import ntut.csie.selab.entity.model.widget.Position;

public class CreateStickyNoteOutput {
    private String stickyNoteId;
    private String boardId;
    private Position position;

    public String getStickyNoteId() {
        return stickyNoteId;
    }

    public String getBoardId() {
        return boardId;
    }

    public Position getPosition() {
        return position;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setStickyNoteId(String stickyNoteId) {
        this.stickyNoteId = stickyNoteId;
    }
}
