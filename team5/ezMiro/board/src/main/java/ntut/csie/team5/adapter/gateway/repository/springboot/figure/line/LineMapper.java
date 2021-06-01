package ntut.csie.team5.adapter.gateway.repository.springboot.figure.line;

import ntut.csie.team5.entity.model.figure.FigureType;
import ntut.csie.team5.entity.model.figure.line.Line;

import java.util.ArrayList;
import java.util.List;

public class LineMapper {

    public static List<Line> transformToDomain(List<LineData> datas) {
        List<Line> lines = new ArrayList<>();
        datas.forEach(lineData -> lines.add(transformToDomain(lineData)));
        return lines;
    }

    public static Line transformToDomain(LineData data) {
        Line line = new Line(
                data.getLineId(),
                data.getBoardId(),
                EndpointMapper.transformToDomain(data.getEndpointDataA()),
                EndpointMapper.transformToDomain(data.getEndpointDataB()),
                FigureType.LINE
        );
        line.clearDomainEvents();
        return line;
    }

    public static LineData transformToData(Line line) {
        LineData lineData = new LineData(
                line.getId(),
                line.getBoardId(),
                EndpointMapper.transformToData(line.getEndpointA()),
                EndpointMapper.transformToData(line.getEndpointB())
        );
        return lineData;
    }
}
