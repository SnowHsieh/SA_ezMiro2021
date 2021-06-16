package ntut.csie.selab.adapter.gateway.repository.springboot.widget;

import ntut.csie.selab.entity.model.widget.Position;
import ntut.csie.selab.entity.model.widget.Line;
import ntut.csie.selab.entity.model.widget.Widget;

import java.util.ArrayList;
import java.util.List;

public class LineDataMapper {

    public static LineData domainToData(Widget widget) {
        Line line = (Line) widget;

        LineData lineData = new LineData(line.getId(), line.getBoardId(),
                line.getPosition().getTopLeft().x, line.getPosition().getTopLeft().y,
                line.getPosition().getBottomRight().x, line.getPosition().getBottomRight().y);
        if (line.getHeadWidgetId() != null) {
            lineData.setHeadWidgetId(line.getHeadWidgetId());
        }
        if (line.getTailWidgetId() != null) {
            lineData.setTailWidgetId(line.getTailWidgetId());
        }

        return lineData;
    }

    public static List<LineData> domainToData(List<Widget> lines) {
        List<LineData> lineDatas = new ArrayList<>();
        lines.forEach(line -> lineDatas.add(domainToData(line)));
        return lineDatas;
    }

    public static Line dataToDomain(LineData lineData) {
        Position position = new Position(lineData.getTopLeftX(), lineData.getTopLeftY(), lineData.getBottomRightX(), lineData.getBottomRightY());

        Line line = new Line(lineData.getLineId(), lineData.getBoardId(), position);
        if (lineData.getHeadWidgetId() != null) {
            line.setHeadWidgetId(lineData.getHeadWidgetId());
        }
        if (lineData.getTailWidgetId() != null) {
            line.setTailWidgetId(lineData.getTailWidgetId());
        }
        return line;
    }
}