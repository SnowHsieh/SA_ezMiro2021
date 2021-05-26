package ntut.csie.sslab.miro.usecase.cursor.move;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;

public interface MoveCursorInput extends Input {
    String getCursorId();

    void setCursorId(String cursorId);

    Coordinate getCoordinate();

    void setCoordinate(Coordinate coordinate);
}