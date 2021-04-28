package ntut.csie.team5.usecase.figure.note.post;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.team5.entity.model.figure.FigureType;

import java.awt.*;

public interface PostNoteInput extends Input {

    String getBoardId();

    void setBoardId(String boardId);

    Point getLeftTopPosition();

    void setLeftTopPosition(Point leftTopPosition);

    int getHeight();

    void setHeight(int height);

    int getWidth();

    void setWidth(int width);

    Color getColor();

    void setColor(Color color);

    FigureType getFigureType();

    void setFigureType(FigureType note);
}
