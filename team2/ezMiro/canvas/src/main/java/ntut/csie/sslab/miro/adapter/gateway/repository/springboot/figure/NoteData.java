package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure;

import ntut.csie.sslab.miro.entity.model.note.Coordinate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="note")
public class NoteData {

    @Column(name="board_id", nullable = false)
    private String boardId;

    @Id
    @Column(name="note_id")
    private String noteId;

    @Column(name="x")
    private double x;

    @Column(name="y")
    private double y;

    @Column(name="color")
    private String color;

    @Column(name="width")
    private double width;

    @Column(name="height")
    private double height;

    @Column(name="description")
    private String description;

    public NoteData(){}

    public NoteData(String boardId, String noteId, String description, String color, Coordinate coordinate, double width, double height) {
        this.boardId = boardId;
        this.noteId = noteId;
        this.x = coordinate.getX();
        this.y = coordinate.getY();
        this.color = color;
        this.width = width;
        this.height = height;
        this.description = description;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}