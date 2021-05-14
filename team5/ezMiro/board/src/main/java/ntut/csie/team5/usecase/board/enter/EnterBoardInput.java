package ntut.csie.team5.usecase.board.enter;

import ntut.csie.sslab.ddd.usecase.Input;

public interface EnterBoardInput extends Input {

    String getBoardId();

    void setBoardId(String boardId);

    String getUserId();

    void setUserId(String userId);
}
