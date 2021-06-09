package ntut.csie.selab.usecase.widget;

import ntut.csie.selab.entity.model.widget.Line;
import ntut.csie.selab.entity.model.widget.Widget;

import java.util.ArrayList;
import java.util.List;

public class LineDtoMapper {
    public LineDto domainToDto(Widget widget) {
        Line line = (Line) widget;
        return new LineDto(
                line.getId(),
                line.getCoordinate().getTopLeft().x,
                line.getCoordinate().getTopLeft().y,
                line.getCoordinate().getBottomRight().x,
                line.getCoordinate().getBottomRight().y,
                line.getHeadWidgetId() == null ? null : line.getHeadWidgetId(),
                line.getTailWidgetId() == null ? null : line.getTailWidgetId()
        );
    }

    public List<LineDto> domainToDto(List<Widget> widgets) {
        List<LineDto> lineDtos = new ArrayList<>();

        widgets.forEach(line -> lineDtos.add(domainToDto(line)));

        return lineDtos;
    }
}
