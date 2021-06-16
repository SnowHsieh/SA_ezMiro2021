package ntut.csie.selab.usecase.widget.line.move;

import ntut.csie.selab.entity.model.widget.Position;

public class MoveLineInput {
    private String lineId;
    private Position position;

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
