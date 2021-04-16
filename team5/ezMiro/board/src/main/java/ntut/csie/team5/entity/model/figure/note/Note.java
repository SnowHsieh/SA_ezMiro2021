package ntut.csie.team5.entity.model.figure.note;

import ntut.csie.sslab.ddd.model.Entity;

import java.awt.*;

public class Note extends Entity<String> {
    private String boardId;
    private Point position;
    private Color color;

    public Note(String noteId, String boardId, Point position, Color color) {
        super(noteId);
        this.boardId = boardId;
        this.position = position;
        this.color = color;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getNoteId() {
        return getId();
    }
}
