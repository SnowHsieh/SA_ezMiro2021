package ntut.csie.sslab.miro.usecase.note.create;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;

public interface CreateNoteInput extends Input {
    String getBoardId();

    void setBoardId(String boardId);

    void setCoordinate(Coordinate coordinate);

    Coordinate getCoordinate();
}
