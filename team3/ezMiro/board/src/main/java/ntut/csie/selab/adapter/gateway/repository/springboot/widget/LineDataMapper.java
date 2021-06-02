package ntut.csie.selab.adapter.gateway.repository.springboot.widget;

import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.Line;
import ntut.csie.selab.entity.model.widget.Widget;

import java.util.ArrayList;
import java.util.List;

public class LineDataMapper {

    public static LineData domainToData(Widget line) {
        return new LineData(line.getId(), line.getBoardId(),
                line.getCoordinate().getTopLeft().x, line.getCoordinate().getTopLeft().y,
                line.getCoordinate().getBottomRight().x, line.getCoordinate().getBottomRight().y);
    }

    public static List<LineData> domainToData(List<Widget> lines) {
        List<LineData> lineDatas = new ArrayList<>();
        lines.forEach(line -> lineDatas.add(domainToData(line)));
        return lineDatas;
    }

    public static Line dataToDomain(LineData lineData) {
        Coordinate coordinate = new Coordinate(lineData.getTopLeftX(), lineData.getTopLeftY(), lineData.getBottomRightX(), lineData.getBottomRightY());
        return new Line(lineData.getLineId(), lineData.getBoardId(), coordinate);
    }
}