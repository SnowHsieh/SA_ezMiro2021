package ntut.csie.sslab.miro.usecase.figure.line;

import ntut.csie.sslab.miro.entity.model.Coordinate;

public class LineDto {
    private final String lineId;
    private final String sourceId;
    private final String targetId;
    private final Coordinate sourcePosition;
    private final Coordinate targetPosition;

    public LineDto(String lineId, String sourceId, String targetId, Coordinate sourcePosition, Coordinate targetPosition) {
        this.lineId = lineId;
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.sourcePosition = sourcePosition;
        this.targetPosition = targetPosition;
    }

    public String getLineId() {
        return lineId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getTargetId() {
        return targetId;
    }

    public Coordinate getSourcePosition() {
        return sourcePosition;
    }

    public Coordinate getTargetPosition() {
        return targetPosition;
    }
}
