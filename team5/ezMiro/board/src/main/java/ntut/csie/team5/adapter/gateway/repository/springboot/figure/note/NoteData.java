package ntut.csie.team5.adapter.gateway.repository.springboot.figure.note;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "note")
public class NoteData {

    @Id
    @Column(name = "note_id")
    private String noteId;

    @Column(name = "board_id")
    private String boardId;

    @Column(name = "left_top_position_x")
    private int leftTopPositionX;

    @Column(name = "left_top_position_y")
    private int leftTopPositionY;

    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    @Column(name = "color")
    private String color;

    @Column(name = "text")
    private String text;

    public NoteData() {
    }

    public NoteData(String noteId, String boardId, int leftTopPositionX, int leftTopPositionY,
                    int width, int height, String color, String text) {
        this.noteId = noteId;
        this.boardId = boardId;
        this.leftTopPositionX = leftTopPositionX;
        this.leftTopPositionY = leftTopPositionY;
        this.width = width;
        this.height = height;
        this.color = color;
        this.text = text;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public int getLeftTopPositionX() {
        return leftTopPositionX;
    }

    public void setLeftTopPositionX(int leftTopPositionX) {
        this.leftTopPositionX = leftTopPositionX;
    }

    public int getLeftTopPositionY() {
        return leftTopPositionY;
    }

    public void setLeftTopPositionY(int leftTopPositionY) {
        this.leftTopPositionY = leftTopPositionY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
