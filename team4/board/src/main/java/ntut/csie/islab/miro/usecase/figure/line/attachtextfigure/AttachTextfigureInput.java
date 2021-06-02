package ntut.csie.islab.miro.usecase.figure.line.attachtextfigure;


import java.util.UUID;

public class AttachTextfigureInput {
    private UUID boardId;
    private UUID figureId;
    private UUID textFigureId;

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

    public UUID getTextFigureId() {
        return textFigureId;
    }

    public void setTextFigureId(UUID textFigureId) {
        this.textFigureId = textFigureId;
    }
}
