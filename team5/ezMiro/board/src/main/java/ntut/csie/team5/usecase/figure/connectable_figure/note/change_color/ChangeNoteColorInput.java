package ntut.csie.team5.usecase.figure.connectable_figure.note.change_color;

import ntut.csie.sslab.ddd.usecase.Input;

public interface ChangeNoteColorInput extends Input {

    String getFigureId();

    void setFigureId(String figureId);

    String getColor();

    void setColor(String color);
}
