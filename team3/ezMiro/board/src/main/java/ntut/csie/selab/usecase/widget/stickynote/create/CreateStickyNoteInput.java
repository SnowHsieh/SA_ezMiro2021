package ntut.csie.selab.usecase.widget.stickynote.create;


import ntut.csie.selab.entity.model.widget.Position;

public class CreateStickyNoteInput {
    private String boardId;
    private Position position;

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getBoardId() {
        return boardId;
    }

    public Position getPosition() {
        return position;
    }

}
