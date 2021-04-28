package ntut.csie.sslab.miro.usecase.board;

import ntut.csie.sslab.ddd.usecase.Input;

public interface CreateBoardInput extends Input {
    String getTeamId();

    void setTeamId(String teamId);

    String getBoardName();

    void setBoardName(String boardName);
}
