package ntut.csie.team5.usecase.figure.connectable_figure.note.move;

import ntut.csie.sslab.ddd.usecase.Input;

import java.awt.*;

public interface MoveNoteInput extends Input {

    String getFigureId();

    void setFigureId(String figureId);

    Point getLeftTopPosition();

    void setLeftTopPosition(Point leftTopPosition);
}
