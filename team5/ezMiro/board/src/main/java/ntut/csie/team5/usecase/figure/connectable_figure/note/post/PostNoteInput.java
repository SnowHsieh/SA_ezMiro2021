package ntut.csie.team5.usecase.figure.connectable_figure.note.post;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.team5.entity.model.figure.FigureType;

public interface PostNoteInput extends Input {

    String getBoardId();

    void setBoardId(String boardId);

//    Point getLeftTopPosition();
//
//    void setLeftTopPosition(Point leftTopPosition);

    int getLeftTopPositionX();

    void setLeftTopPositionX(int leftTopPositionX);

    int getLeftTopPositionY();

    void setLeftTopPositionY(int leftTopPositionY);

    int getHeight();

    void setHeight(int height);

    int getWidth();

    void setWidth(int width);

    String getColor();

    void setColor(String color);

    FigureType getFigureType();

    void setFigureType(FigureType note);
}
