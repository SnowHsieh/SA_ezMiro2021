package ntut.csie.selab.usecase.widget.line.move;

import ntut.csie.selab.entity.model.widget.Coordinate;

public class MoveLineInput {
    private String lineId;
    private Coordinate coordinate;

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
