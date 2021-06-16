package ntut.csie.selab.usecase.widget.stickynote.resize;

import ntut.csie.selab.entity.model.widget.Position;

public class ResizeStickyNoteInput {
    private String id;
    private Position position;

    public String getStickyNoteId() {
        return id;
    }

    public void setStickyNoteId(String id) {
        this.id = id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
