package ntut.csie.team5.usecase.board.getcontent;

import ntut.csie.sslab.ddd.usecase.Input;

public interface GetBoardContentInput extends Input {

    String getBoardId();

    void setBoardId(String boardId);
}
