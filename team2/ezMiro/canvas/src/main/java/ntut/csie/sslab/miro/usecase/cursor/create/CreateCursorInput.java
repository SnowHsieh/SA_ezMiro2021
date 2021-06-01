package ntut.csie.sslab.miro.usecase.cursor.create;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;

public interface CreateCursorInput extends Input {
    String getBoardId();

    void setBoardId(String boardId);

    String getUserId();

    void setUserId(String userId);

    Coordinate getCoordinate();

    void setCoordinate(Coordinate coordinate);
}