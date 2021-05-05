package ntut.csie.sslab.miro.entity.model.note.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class NoteSizeChanged extends DomainEvent {
    private final String noteId;
    private final double height;
    private final double width;
    private final String boardId;

    public NoteSizeChanged(String noteId, double height, double width, String boardId) {
        super(DateProvider.now());
        this.noteId = noteId;
        this.height = height;
        this.width = width;
        this.boardId = boardId;
    }

    public String noteId() {
        return noteId;
    }

    public double height() {
        return height;
    }

    public double width() {
        return width;
    }

    public String boardId() {
        return boardId;
    }
}