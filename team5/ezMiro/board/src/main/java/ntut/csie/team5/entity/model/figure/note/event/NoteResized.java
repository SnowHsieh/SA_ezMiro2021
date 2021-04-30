package ntut.csie.team5.entity.model.figure.note.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.team5.entity.model.figure.FigureType;

public class NoteResized extends DomainEvent {

    private final String figureId;
    private final int oldHeight;
    private final int oldWidth;
    private final int newHeight;
    private final int newWidth;
    private final String boardId;
    private final FigureType figureType;

    public NoteResized(String figureId, int oldHeight, int oldWidth, int newHeight, int newWidth, String boardId, FigureType figureType) {
        super(DateProvider.now());
        this.figureId = figureId;
        this.boardId = boardId;
        this.oldHeight = oldHeight;
        this.oldWidth = oldWidth;
        this.newHeight = newHeight;
        this.newWidth = newWidth;
        this.figureType = figureType;
    }

    public String figureId() {
        return figureId;
    }

    public int oldHeight() {
        return oldHeight;
    }

    public int oldWidth() {
        return oldWidth;
    }

    public int newHeight() {
        return newHeight;
    }

    public int newWidth() {
        return newWidth;
    }

    public String boardId() {
        return boardId;
    }

    public FigureType figureType() {
        return figureType;
    }
}
