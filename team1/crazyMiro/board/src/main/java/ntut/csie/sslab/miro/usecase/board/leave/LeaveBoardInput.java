package ntut.csie.sslab.miro.usecase.board.leave;

import ntut.csie.sslab.ddd.usecase.Input;

public interface LeaveBoardInput extends Input {
    String getBoardId();

    void setBoardId(String boardId);

    String getBoardSessionId();

    void setBoardSessionId(String boardSessionId);
}
