package ntut.csie.sslab.miro.entity.model.figure.connectablefigure.note;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.connectablefigure.ConnectableFigure;
import ntut.csie.sslab.miro.entity.model.figure.connectablefigure.note.event.NoteEvents;

import java.util.UUID;

public class Note extends ConnectableFigure {
    private String description;


    public Note(String boardId, String noteId, String description, String color, Coordinate coordinate, double width, double height) {
        super(noteId, boardId, coordinate, color, width, height);
        this.description = description;
        addDomainEvent(new NoteEvents.NoteCreated(UUID.randomUUID(), noteId, description, color, coordinate, width, height, boardId, DateProvider.now()));
    }

    public String getDescription() {
        return description;
    }

    public void changeDescription(String description) {
        if(!this.description.equals(description)){
            this.setDescription(description);
            addDomainEvent(new NoteEvents.NoteDescriptionChanged(UUID.randomUUID(), getId(), description, getBoardId(), DateProvider.now()));
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void changeColor(String color) {
        if(!getColor().equals(color)){
            this.setColor(color);
            addDomainEvent(new NoteEvents.NoteColorChanged(UUID.randomUUID(), getId(), color, getBoardId(), DateProvider.now()));
        }
    }

    public void changeSize(double height, double width) {
        if(getHeight() != height || getWidth() != width) {
            this.setHeight(height);
            this.setWidth(width);
            addDomainEvent(new NoteEvents.NoteSizeChanged(UUID.randomUUID(), getId(), height, width, getBoardId(), DateProvider.now()));
        }
    }

    public void bringToFront() {
        addDomainEvent(new NoteEvents.NoteBroughtToFront(UUID.randomUUID(), getId(), getBoardId(), DateProvider.now()));
    }

    public void sendToBack() {
        addDomainEvent(new NoteEvents.NoteSentToBack(UUID.randomUUID(), getId(), getBoardId(), DateProvider.now()));
    }

    public void move(Coordinate coordinate) {
        if(!getCoordinate().equals(coordinate)) {
            this.setCoordinate(coordinate);
            addDomainEvent(new NoteEvents.NoteMoved(UUID.randomUUID(), getId(), coordinate, getBoardId(), DateProvider.now()));
        }
    }

    public void markAsRemoved(String boardId) {
        addDomainEvent(new NoteEvents.NoteDeleted(UUID.randomUUID(), getId(), boardId, DateProvider.now()));
    }
}