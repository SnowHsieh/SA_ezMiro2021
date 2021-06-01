package ntut.csie.team5.usecase.figure.connectable_figure.note.delete;

import ntut.csie.sslab.ddd.usecase.Input;

public interface DeleteNoteInput extends Input {

    String getFigureId();

    void setFigureId(String figureId);
}
