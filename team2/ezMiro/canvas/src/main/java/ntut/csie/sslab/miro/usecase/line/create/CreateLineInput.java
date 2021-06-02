package ntut.csie.sslab.miro.usecase.line.create;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;

public interface CreateLineInput extends Input {
    String getBoardId();

    void setBoardId(String boardId);

    String getStartConnectableFigureId();

    void setStartConnectableFigureId(String figureId);

    String getEndConnectableFigureId();

    void setEndConnectableFigureId(String figureId);

    Coordinate getStartOffset();

    void setStartOffset(Coordinate offset);

    Coordinate getEndOffset();

    void setEndOffset(Coordinate offset);
}