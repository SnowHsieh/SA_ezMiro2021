package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure;

import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.line.ArrowStyle;
import ntut.csie.sslab.miro.entity.model.figure.line.Line;
import java.util.ArrayList;
import java.util.List;

public class LineMapper {
    public static List<Line> transformToDomain(List<LineData> datas) {
        List<Line> lines = new ArrayList<>();
        datas.forEach(x -> lines.add(transformToDomain(x)));
        return lines;
    }

    public static Line transformToDomain(LineData data) {
        Coordinate startOffset = new Coordinate(data.getStartOffsetX(), data.getStartOffsetY());
        Coordinate endOffset = new Coordinate(data.getEndOffsetX(), data.getEndOffsetY());
        ArrowStyle startArrowStyle = ArrowStyle.valueOf(data.getStartArrowStyle());
        ArrowStyle endArrowStyle = ArrowStyle.valueOf(data.getEndArrowStyle());
        Line line = new Line(data.getLineId(), data.getBoardId(), data.getStartConnectableFigureId(), data.getEndConnectableFigureId(),
                startOffset, endOffset, startArrowStyle, endArrowStyle);
        line.clearDomainEvents();
        return line;
    }

    public static LineData transformToData(Line line) {
        LineData data = new LineData(
                line.getBoardId(),
                line.getId(),
                line.getStartConnectableFigureId(),
                line.getEndConnectableFigureId(),
                line.getStartOffset(),
                line.getEndOffset(),
                line.getStartArrowStyle().name(),
                line.getEndArrowStyle().name());
        return data;
    }
}