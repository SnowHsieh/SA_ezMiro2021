package ntut.csie.sslab.miro.usecase.board.showCursor;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.sslab.miro.entity.model.Coordinate;

public interface ShowCursorInput extends Input {
    void setBoardId(String boardId);

    void setUserId(String userId);

    void setPosition(Coordinate position);

    String getBoardId();

    String getUserId();

    Coordinate getPosition();
}
