package ntut.csie.sslab.miro.entity.model.note.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

import java.util.Date;

public class NoteColorChanged extends DomainEvent {
    private final String noteId;
    private final String originalColor;
    private final String newColor;
    private final String boardId;

    public NoteColorChanged(String noteId, String originalColor, String newColor, String boardId) {
        super(DateProvider.now());
        this.noteId = noteId;
        this.originalColor = originalColor;
        this.newColor = newColor;
        this.boardId = boardId;
    }

    public String noteId() {
        return noteId;
    }

    public String originalColor() {
        return originalColor;
    }

    public String newColor() {
        return newColor;
    }

    public String boardId() {
        return boardId;
    }
}
