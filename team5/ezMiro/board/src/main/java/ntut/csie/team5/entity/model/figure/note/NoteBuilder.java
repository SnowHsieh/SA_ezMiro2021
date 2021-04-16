package ntut.csie.team5.entity.model.figure.note;

import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.entity.model.board.BoardBuilder;

import java.awt.*;
import java.util.UUID;

public class NoteBuilder {
    private String noteId;
    private String boardId;
    private Point position;
    private Color color;

    public static NoteBuilder newInstance() {
        return new NoteBuilder();
    }

    public NoteBuilder boardId(String boardId) {
        this.boardId = boardId;
        return this;
    }

    public NoteBuilder position(Point position) {
        this.position = position;
        return this;
    }

    public NoteBuilder color(Color color) {
        this.color = color;
        return this;
    }

    public Note build() {
        noteId = UUID.randomUUID().toString();
        Note note = new Note(noteId, boardId, position, color);
        return note;
    }
}
