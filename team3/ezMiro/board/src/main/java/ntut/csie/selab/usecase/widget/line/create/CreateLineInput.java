package ntut.csie.selab.usecase.widget.line.create;

import ntut.csie.selab.entity.model.widget.Position;

public class CreateLineInput {
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
