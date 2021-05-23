package ntut.csie.sslab.miro.usecase.board.enter;

import ntut.csie.sslab.ddd.usecase.Input;

public interface EnterBoardInput extends Input {
    void setUserId(String userId);

    String getUserId();

    void setBoardId(String boardId);

    String getBoardId();
}
