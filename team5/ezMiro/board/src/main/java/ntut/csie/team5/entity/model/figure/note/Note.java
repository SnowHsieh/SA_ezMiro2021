package ntut.csie.team5.entity.model.figure.note;

import ntut.csie.team5.entity.model.figure.ConnectableFigure;
import ntut.csie.team5.entity.model.figure.FigureType;
import ntut.csie.team5.entity.model.figure.note.event.*;

public class Note extends ConnectableFigure {

    private String color;
    private String text;

    public Note(String noteId, String boardId, int leftTopPositionX, int leftTopPositionY, int height, int width, String color, FigureType figureType) {
        super(noteId, boardId, leftTopPositionX, leftTopPositionY, height, width, figureType);
        this.color = color;
        this.text = "";
        addDomainEvent(new NotePosted(noteId, boardId, leftTopPositionX, leftTopPositionY, height, width, color, figureType));
    }

    public void changeColor(String newColor) {
        if(!color.equals(newColor)) {
            String oldColor = color;
            this.setColor(newColor);
            addDomainEvent(new NoteColorChanged(getId(), oldColor, newColor, getBoardId(), getFigureType()));
        }
    }

    public void changePosition(int newLeftTopPositionX, int newLeftTopPositionY) {
        if(this.getLeftTopPositionX() != newLeftTopPositionX || this.getLeftTopPositionY() != newLeftTopPositionY) {
            int oldLeftTopPositionX = this.getLeftTopPositionX();
            int oldLeftTopPositionY = this.getLeftTopPositionY();
            this.setLeftTopPositionX(newLeftTopPositionX);
            this.setLeftTopPositionY(newLeftTopPositionY);
            addDomainEvent(new NoteMoved(getId(), oldLeftTopPositionX, oldLeftTopPositionY, newLeftTopPositionX, newLeftTopPositionY, getBoardId(), getFigureType()));
        }
    }

    public void changeSize(int newHeight, int newWidth) {
        if(this.getHeight() != newHeight && this.getWidth() != newWidth) {
            int oldHeight = this.getHeight();
            int oldWidth = this.getWidth();
            this.setHeight(newHeight);
            this.setWidth(newWidth);
            addDomainEvent(new NoteResized(getId(), oldHeight, oldWidth, newHeight, newWidth, getBoardId(), getFigureType()));
        }
    }

    public void editText(String newText) {
        if(!text.equals(newText)) {
            String oldText = text;
            this.setText(newText);
            addDomainEvent(new NoteTextEdited(getId(), oldText, newText, getBoardId(), getFigureType()));
        }
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
