package ntut.csie.selab.usecase.board;

import ntut.csie.selab.entity.model.board.CommittedWidget;

import java.util.ArrayList;
import java.util.List;

public class CommittedWidgetMapper {

    public CommittedWidgetDto domainToDto(CommittedWidget committedWidget) {
        return new CommittedWidgetDto(committedWidget.getWidgetId(), committedWidget.getZOrder());
    }

    public List<CommittedWidgetDto> domainToDto(List<CommittedWidget> committedWidgets) {
        List<CommittedWidgetDto> committedWidgetDtos = new ArrayList<>();
        committedWidgets.forEach(committedWidget -> committedWidgetDtos.add(domainToDto(committedWidget)));
        return committedWidgetDtos;
    }
}
