package ntut.csie.team5.usecase.figure.connectable_figure.note.delete;

import ntut.csie.sslab.ddd.usecase.Input;

import java.awt.*;

public interface DeleteNoteInput extends Input {
    String getBoardId();

    void setBoardId(String boardId);

    String getFigureId();

    void setFigureId(String figureId);
}
