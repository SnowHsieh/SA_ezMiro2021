package ntut.csie.islab.miro.entity.model.figure.textfigure;


import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.figure.Figure;

import java.util.UUID;

public abstract class TextFigure extends Figure {

    public TextFigure(UUID boardId, Position position, String content, Style style) {
        super(boardId, position, content, style);
    }

    public TextFigure(UUID boardId, UUID stickyNoteId, Position position, String content, Style style) {
        super(boardId, stickyNoteId, position, content, style);
    }

    public abstract void markAsRemoved(UUID boardId, UUID figureId);

    public abstract void changeContent(String newContent);

    public abstract void changePosition(Position newPosition);

    public abstract void changeColor(String newColor);

    public abstract void resize(Double newWidth, Double newHeight);
}
