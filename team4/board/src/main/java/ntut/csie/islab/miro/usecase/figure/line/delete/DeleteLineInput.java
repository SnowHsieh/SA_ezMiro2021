package ntut.csie.islab.miro.usecase.figure.line.delete;

import java.util.UUID;

public class DeleteLineInput {
    private UUID boardId;
    private UUID figureId;

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
}
