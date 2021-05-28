package ntut.csie.islab.miro.usecase.textfigure.stickynote.move;

import ntut.csie.islab.miro.entity.model.Position;

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
