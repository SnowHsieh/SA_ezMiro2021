package ntut.csie.islab.miro.usecase.textFigure.stickyNote;

import ntut.csie.islab.miro.entity.model.textFigure.Position;

import java.util.UUID;

public class MoveStickyNoteInput {
    private UUID boardId;
    private UUID FigureId;
    private Position newPosition;

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    public UUID getFigureId() {
        return FigureId;
    }

    public void setFigureId(UUID figureId) {
        FigureId = figureId;
    }

    public Position getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(Position newPosition) {
        this.newPosition = newPosition;
    }
}
