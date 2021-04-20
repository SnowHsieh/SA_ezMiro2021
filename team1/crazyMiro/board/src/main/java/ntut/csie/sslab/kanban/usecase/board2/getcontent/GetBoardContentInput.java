package ntut.csie.sslab.kanban.usecase.board2.getcontent;

import ntut.csie.sslab.ddd.usecase.Input;

public interface GetBoardContentInput extends Input {

    String getBoardId();

    void setBoardId(String boardId);
}
