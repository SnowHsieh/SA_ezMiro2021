package ntut.csie.sslab.kanban.usecase.figure.sendtoback;

import ntut.csie.sslab.ddd.usecase.Input;

public interface SendFigureToBackInput extends Input {
    void setFigureId(String stickerId1);

    void setBoardId(String boardId1);

    String getBoardId();

    String getFigureId();
}
