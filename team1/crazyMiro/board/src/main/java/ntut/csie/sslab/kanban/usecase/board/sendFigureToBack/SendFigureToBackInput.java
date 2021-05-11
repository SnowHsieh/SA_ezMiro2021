package ntut.csie.sslab.kanban.usecase.board.sendFigureToBack;

import ntut.csie.sslab.ddd.usecase.Input;

public interface SendFigureToBackInput extends Input {
    String getBoardId();

    void setBoardId(String boardId);

    String getFigureId();

    void setFigureId(String figureId);
}
