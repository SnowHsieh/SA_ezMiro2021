package ntut.csie.selab.adapter.gateway.repository.springboot.widget;

import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;

import java.util.ArrayList;
import java.util.List;

public class WidgetDataMapper {

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

    public static Widget dataToDomain(WidgetData widgetData) {
        Coordinate coordinate = new Coordinate(widgetData.getTopLeftX(), widgetData.getTopLeftY(), widgetData.getBottomRightX(), widgetData.getBottomRightY());
        // TODO 要把widget型態(sticky note, line, etc.)存入資料庫，用Mapper時用Factory生不同的instance，這邊先stickynote寫死
        Widget widget = new StickyNote(widgetData.getWidgetId(), widgetData.getBoardId(), coordinate);
        return widget;
    }
}
