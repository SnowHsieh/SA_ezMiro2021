package ntut.csie.selab.usecase.widget.resize;

import ntut.csie.selab.entity.model.widget.Coordinate;

public class ResizeStickyNoteInput {
    private String id;
    private Coordinate coordinate;

    public String getStickyNoteId() {
        return id;
    }

    public void setStickyNoteId(String id) {
        this.id = id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
