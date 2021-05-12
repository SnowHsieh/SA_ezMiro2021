package ntut.csie.sslab.miro.entity.model.note;

import ntut.csie.sslab.miro.entity.model.figure.Figure;
import ntut.csie.sslab.miro.entity.model.note.event.*;

public class Note extends Figure {
    private String description;

    public Note(String boardId, String noteId, String description, String color, Coordinate coordinate, double width, double height) {
        super(noteId, boardId, coordinate, color, width, height);
        this.description = description;

        addDomainEvent(new NoteCreated(boardId, noteId, description, color, coordinate, width, height));
    }

    public String getDescription() {
        return description;
    }

    public void changeDescription(String description) {
        if(!this.description.equals(description)){
            this.setDescription(description);
            addDomainEvent(new NoteDescriptionChanged(getId(), description, getBoardId()));
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void changeColor(String color) {
        if(!getColor().equals(color)){
            this.setColor(color);
            addDomainEvent(new NoteColorChanged(getId(), color, getBoardId()));
        }
    }

    public void changeSize(double height, double width) {
        if(getHeight() != height || getWidth() != width) {
            this.setHeight(height);
            this.setWidth(width);
            addDomainEvent(new NoteSizeChanged(getId(), height, width, getBoardId()));
        }
    }

    public void bringToFront() {
        addDomainEvent(new NoteBroughtToFront(getId(), getBoardId()));
    }

    public void sendToBack() {
        addDomainEvent(new NoteSentToBack(getId(), getBoardId()));
    }

    public void move(Coordinate coordinate) {
        if(!getCoordinate().equals(coordinate)) {
            this.setCoordinate(coordinate);
            addDomainEvent(new NoteMoved(getId(), coordinate, getBoardId()));
        }
    }

    public void markAsRemoved(String boardId) {
        addDomainEvent(new NoteDeleted(boardId, getId()));
    }
}