package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.line;

import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.Line;

import java.util.ArrayList;
import java.util.List;

public class LineMapper {

    public static Line transformToDomain(LineData lineData) {
        Coordinate sourcePosition = new Coordinate(lineData.getSourceX(), lineData.getSourceY());
        Coordinate targetPosition = new Coordinate(lineData.getTargetX(), lineData.getTargetY());
        Line line = new Line(lineData.getBoardId(),
                lineData.getFigureId(),
                lineData.getSourceId(),
                lineData.getTargetId(),
                sourcePosition,
                targetPosition);
        line.clearDomainEvents();
        return line;
    }

    public static List<Line> transformToDomain(List<LineData> lineDatas) {
        List<Line> lines = new ArrayList<>();
        for(LineData lineData : lineDatas) {
            Coordinate sourcePosition = new Coordinate(lineData.getSourceX(), lineData.getSourceY());
            Coordinate targetPosition = new Coordinate(lineData.getTargetX(), lineData.getTargetY());
            Line line = new Line(lineData.getBoardId(),
                    lineData.getFigureId(),
                    lineData.getSourceId(),
                    lineData.getTargetId(),
                    sourcePosition,
                    targetPosition);
            line.clearDomainEvents();
            lines.add(line);
        }
        return lines;
    }

    public static LineData transformToData(Line line) {
        return new LineData(line.getFigureId(),
                line.getBoardId(),
                line.getSourceId(),
                line.getTargetId(),
                line.getSourcePosition().getX(),
                line.getSourcePosition().getY(),
                line.getTargetPosition().getX(),
                line.getTargetPosition().getY());
    }
}
