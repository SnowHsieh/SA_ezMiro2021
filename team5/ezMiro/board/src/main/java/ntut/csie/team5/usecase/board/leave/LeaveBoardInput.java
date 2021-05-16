package ntut.csie.team5.usecase.board.leave;

import ntut.csie.sslab.ddd.usecase.Input;

public interface LeaveBoardInput extends Input {

    String getBoardId();

    void setBoardId(String boardId);

    String getBoardSessionId();

    void setBoardSessionId(String boardSessionId);

    String getUserId();

    void setUserId(String userId);
}
