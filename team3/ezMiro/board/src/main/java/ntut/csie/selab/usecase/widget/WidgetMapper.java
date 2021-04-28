package ntut.csie.selab.usecase.widget;

import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.usecase.widget.WidgetDto;

import java.util.ArrayList;
import java.util.List;

public class WidgetMapper {

    public WidgetDto domainToDto(Widget widget) {
        Coordinate coordinate = widget.getCoordinate();
        int width = coordinate.getBottomRight().x - coordinate.getTopLeft().x;
        int height = coordinate.getBottomRight().y - coordinate.getTopLeft().y;
        return new WidgetDto(coordinate.getTopLeft().x, coordinate.getTopLeft().y, width, height);
    }

    public List<WidgetDto> domainToDto(List<Widget> widgets) {
        List<WidgetDto> widgetDtos = new ArrayList<>();

        widgets.forEach(widget -> widgetDtos.add(domainToDto(widget)));

        return widgetDtos;
    }

}
