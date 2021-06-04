package ntut.csie.sslab.miro.usecase.note.move;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;

public interface MoveNoteInput extends Input {
    void setNoteId(String noteId);

    String getNoteId();

    void setCoordinate(Coordinate coordinate);

    Coordinate getCoordinate();
}