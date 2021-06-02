package ntut.csie.selab.entity.model.widget;

import ntut.csie.selab.entity.model.widget.event.LineLinked;

import java.util.Date;

public class Line extends Widget {

    private Widget headWidget;
    private Widget tailWidget;

    public Line(String id, String boardId, Coordinate coordinate) {
        super(id, boardId, coordinate, WidgetType.LINE.getType());
    }

    public Widget getHeadWidget() {
        return headWidget;
    }

    public void setHeadWidget(Widget headWidget) {
        this.headWidget = headWidget;
    }

    public Widget getTailWidget() {
        return tailWidget;
    }

    public void setTailWidget(Widget tailWidget) {
        this.tailWidget = tailWidget;
    }

    public void link(String endPoint, Widget widget) {
        if(endPoint.equals("head")) {
            headWidget = widget;
        } else {
            tailWidget = widget;
        }

        addDomainEvent((new LineLinked(
                new Date(),
                this.boardId,
                getId()
        )));
    }
}
