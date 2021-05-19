package ntut.csie.sslab.kanban.usecase.cursor.move;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.sslab.kanban.entity.model.Coordinate;

public interface MoveCursorInput extends Input {

    String getUserId();

    void setUserId(String userId);

    Coordinate getPosition();

    void setPosition(Coordinate position);
}
