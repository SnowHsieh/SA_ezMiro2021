package ntut.csie.selab.entity.model.widget;

public class Line extends Widget {

    public Line(String id, String boardId, Coordinate coordinate) {
        super(id, boardId, coordinate, WidgetType.LINE.getType());
    }
}
