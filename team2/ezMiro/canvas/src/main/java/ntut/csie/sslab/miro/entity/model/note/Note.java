package ntut.csie.sslab.miro.entity.model.note;

import ntut.csie.sslab.miro.entity.model.figure.Figure;
import ntut.csie.sslab.miro.entity.model.note.event.NoteColorChanged;
import ntut.csie.sslab.miro.entity.model.note.event.NoteCreated;
import ntut.csie.sslab.miro.entity.model.note.event.NoteDescriptionChanged;

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

    public void changeColor(String newColor) {
        if(!getColor().equals(newColor)){
            String originalColor = getColor();
            this.setColor(newColor);
            addDomainEvent(new NoteColorChanged(getId(), originalColor, newColor, getBoardId()));
        }
    }
}
