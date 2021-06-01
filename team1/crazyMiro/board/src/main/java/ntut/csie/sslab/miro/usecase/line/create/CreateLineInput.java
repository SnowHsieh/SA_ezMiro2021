package ntut.csie.sslab.miro.usecase.line.create;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.sslab.miro.entity.model.Coordinate;

public interface CreateLineInput extends Input {

    String getBoardId();

    void setBoardId(String boardId);

    String getSourceId();

    void setSourceId(String sourceId);

    String getTargetId();

    void setTargetId(String targetId);

    Coordinate getSourcePosition();

    void setSourcePosition(Coordinate sourcePosition);

    Coordinate getTargetPosition();

    void setTargetPosition(Coordinate targetPosition);
}
