package ntut.csie.selab.adapter.gateway.repository.springboot.widget;

import ntut.csie.selab.entity.model.widget.Widget;

import java.util.ArrayList;
import java.util.List;

public class WidgetMapper {

    public static WidgetData domainToData(Widget widget) {
        return new WidgetData(
                widget.getId(),
                widget.getBoardId(),
                widget.getCoordinate().getTopLeft().x,
                widget.getCoordinate().getTopLeft().y,
                widget.getCoordinate().getBottomRight().x,
                widget.getCoordinate().getBottomRight().y,
                widget.getColor(),
                widget.getTextColor(),
                widget.getText(),
                widget.getFontSize()
        );
    }

    public static List<WidgetData> domainToData(List<Widget> widgets) {
        List<WidgetData> widgetDatas = new ArrayList<>();
        widgets.forEach(widget -> widgetDatas.add(domainToData(widget)));
        return widgetDatas;
    }
}
