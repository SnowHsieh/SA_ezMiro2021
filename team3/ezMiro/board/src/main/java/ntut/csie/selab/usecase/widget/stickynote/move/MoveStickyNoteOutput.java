package ntut.csie.selab.usecase.widget.stickynote.move;

import ntut.csie.selab.entity.model.widget.Position;

public class MoveStickyNoteOutput {
    private String stickyNoteId;
    private Position position;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getStickyNoteId() {
        return stickyNoteId;
    }

    public void setStickyNoteId(String stickyNoteId) {
        this.stickyNoteId = stickyNoteId;
    }
}
