package ntut.csie.team5.entity.model.figure.note.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.team5.entity.model.figure.FigureType;

import java.awt.*;

public class NoteTextEdited extends DomainEvent {
    private final String figureId;
    private final String oldText;
    private final String newText;
    private final String boardId;
    private final FigureType figureType;

    public NoteTextEdited(String figureId, String oldText, String newText, String boardId, FigureType figureType) {
        super(DateProvider.now());
        this.figureId = figureId;
        this.boardId = boardId;
        this.oldText = oldText;
        this.newText = newText;
        this.figureType = figureType;
    }

    public String figureId() {
        return figureId;
    }

    public String oldText() {
        return oldText;
    }

    public String newText() {
        return newText;
    }

    public String boardId() {
        return boardId;
    }

    public FigureType figureType() {
        return figureType;
    }
}
