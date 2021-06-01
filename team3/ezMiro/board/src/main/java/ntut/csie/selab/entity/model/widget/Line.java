package ntut.csie.selab.entity.model.widget;

import ntut.csie.selab.entity.model.widget.event.LineCreated;

public class Line extends Widget {

    public Line(String id, String boardId, Coordinate coordinate) {
        super(id, boardId, coordinate, "Line");
    }
}
