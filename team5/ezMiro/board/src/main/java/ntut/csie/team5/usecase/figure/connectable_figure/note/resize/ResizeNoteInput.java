package ntut.csie.team5.usecase.figure.connectable_figure.note.resize;

import ntut.csie.sslab.ddd.usecase.Input;

public interface ResizeNoteInput extends Input {

    String getFigureId();

    void setFigureId(String figureId);

    int getHeight();

    void setHeight(int height);

    int getWidth();

    void setWidth(int width);
}
