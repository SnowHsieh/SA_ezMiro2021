package ntut.csie.team5.usecase.board.create;

import ntut.csie.sslab.ddd.usecase.Input;

public interface CreateBoardInput extends Input {

    String getProjectId();

    void setProjectId(String projectId);

    String getBoardName();

    void setBoardName(String boardName);
}
