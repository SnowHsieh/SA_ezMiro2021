package ntut.csie.team5.entity.model.figure.note.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.team5.entity.model.figure.FigureType;

public class NoteColorChanged extends DomainEvent {

    private final String figureId;
    private final String oldColor;
    private final String newColor;
    private final String boardId;
    private final FigureType figureType;

    public NoteColorChanged(String figureId, String oldColor, String newColor, String boardId, FigureType figureType) {
        super(DateProvider.now());
        this.figureId = figureId;
        this.boardId = boardId;
        this.oldColor = oldColor;
        this.newColor = newColor;
        this.figureType = figureType;
    }

    public String figureId() {
        return figureId;
    }

    public String oldColor() {
        return oldColor;
    }

    public String newColor() {
        return newColor;
    }

    public String boardId() {
        return boardId;
    }

    public FigureType figureType() {
        return figureType;
    }
}
