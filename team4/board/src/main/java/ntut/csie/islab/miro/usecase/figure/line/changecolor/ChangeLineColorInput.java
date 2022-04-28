package ntut.csie.islab.miro.usecase.figure.line.changecolor;

import java.util.UUID;

public class ChangeLineColorInput {
    private UUID boardId;
    private UUID figureId;
    private String color;

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
