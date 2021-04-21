package ntut.csie.sslab.miro.entity.model.note;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.miro.entity.model.note.event.NoteColorChanged;
import ntut.csie.sslab.miro.entity.model.note.event.NoteCreated;
import ntut.csie.sslab.miro.entity.model.note.event.NoteDescriptionChanged;

public class Note extends AggregateRoot<String> {
    private String boardId;
    private String description;
    private String color;

    public Note(String boardId, String noteId, String description, String color) {
        super(noteId);
        this.boardId = boardId;
        this.description = description;
        this.color = color;

        addDomainEvent(new NoteCreated(boardId, noteId, description, color));
    }

    public String getBoardId() {
        return boardId;
    }

    public String getDescription() {
        return description;
    }

    public void changeDescription(String newDescription, String boardId) {
        if(!description.equals(newDescription)){
            String originalDescription = description;
            this.setDescription(newDescription);
            addDomainEvent(new NoteDescriptionChanged(getId(), originalDescription, newDescription, boardId));
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void changeColor(String newColor) {
        if(!color.equals(newColor)){
            String originalColor = color;
            this.setColor(newColor);
            addDomainEvent(new NoteColorChanged(getId(), originalColor, newColor, boardId));
        }
    }

    public void setColor(String color) {
        this.color = color;
    }
}
