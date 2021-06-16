package ntut.csie.selab.usecase.widget.line.create;

import ntut.csie.selab.entity.model.widget.Position;

public class CreateLineOutput {
    private String lineId;
    private String boardId;
    private Position position;

    public String getLineId() {
        return lineId;
    }

    public String getBoardId() {
        return boardId;
    }

    public Position getPosition() {
        return position;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
