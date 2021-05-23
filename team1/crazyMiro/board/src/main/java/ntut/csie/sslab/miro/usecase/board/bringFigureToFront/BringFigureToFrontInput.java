package ntut.csie.sslab.miro.usecase.board.bringFigureToFront;

import ntut.csie.sslab.ddd.usecase.Input;

public interface BringFigureToFrontInput extends Input {

    String getBoardId();

    void setBoardId(String boardId);

    String getFigureId();

    void setFigureId(String figureId);
}
