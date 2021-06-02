package ntut.csie.selab.usecase.widget.stickynote.move;

import ntut.csie.selab.entity.model.widget.Coordinate;

public class MoveStickyNoteInput {
    private String stickyNoteId;
    private Coordinate coordinate;

    public String getStickyNoteId() {
        return stickyNoteId;
    }

    public void setStickyNoteId(String stickyNoteId) {
        this.stickyNoteId = stickyNoteId;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
