package ntut.csie.selab.usecase.widget;

import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.Widget;

import java.util.ArrayList;
import java.util.List;

public class WidgetDeletedDtoMapper {

    public WidgetDeletedDto domainToDto(String widgetId) {
        return new WidgetDeletedDto(
                widgetId
        );
    }

    public List<WidgetDeletedDto> domainToDto(List<String> widgetIds) {
        List<WidgetDeletedDto> widgetDeletedDtos = new ArrayList<>();
        widgetIds.forEach(widgetId -> widgetDeletedDtos.add(domainToDto(widgetId)));
        return widgetDeletedDtos;
    }

}
