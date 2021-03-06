package ntut.csie.sslab.miro.usecase.board.create;

import ntut.csie.sslab.ddd.usecase.Input;

public interface CreateBoardInput extends Input {
    String getBoardId();

    void setBoardId(String id);

    String getBoardName();

    void setBoardName(String boardName);
}
