package ntut.csie.sslab.kanban.usecase.cursor.move;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.sslab.kanban.entity.model.Coordinate;

public interface MoveCursorInput extends Input {

    String getCursorId();

    void setCursorId(String cursorId);

    Coordinate getPosition();

    void setPosition(Coordinate position);
}
