package ntut.csie.selab.adapter.gateway.repository.springboot.board;

import ntut.csie.selab.adapter.gateway.repository.springboot.widget.CommittedWidgetData;
import ntut.csie.selab.entity.model.board.CommittedWidget;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommittedWidgetDataMapper {
    public static CommittedWidgetData domainToData(CommittedWidget committedWidget) {
        return new CommittedWidgetData(committedWidget.getBoardId(), committedWidget.getWidgetId(), committedWidget.getZOrder());
    }

    public static List<CommittedWidgetData> domainToData(List<CommittedWidget> committedWidgets) {
        List<CommittedWidgetData> committedWidgetDatas = new ArrayList<>();
        committedWidgets.forEach(committedWidget -> committedWidgetDatas.add(domainToData(committedWidget)));
        return committedWidgetDatas;
    }

    public static CommittedWidget dataToDomain(CommittedWidgetData committedWidgetData) {
        return new CommittedWidget(committedWidgetData.getId().getBoardId(), committedWidgetData.getId().getWidgetId(), committedWidgetData.getzOrder());
    }

    public static Set<CommittedWidget> dataToDomain(Set<CommittedWidgetData> committedWidgetDatas) {
        Set<CommittedWidget> committedWidgets = new HashSet<>();
        committedWidgetDatas.forEach(committedWidgetData -> committedWidgets.add(dataToDomain(committedWidgetData)));
        return committedWidgets;
    }
}
