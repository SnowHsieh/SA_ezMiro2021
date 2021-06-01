package ntut.csie.team5.usecase.board.move_cursor;

import ntut.csie.sslab.ddd.usecase.Input;

public interface MoveCursorInput extends Input {

    String getBoardId();

    void setBoardId(String boardId);

    String getBoardSessionId();

    void setBoardSessionId(String boardSessionId);

    int getPositionX();

    void setPositionX(int positionX);

    int getPositionY();

    void setPositionY(int positionY);
}
