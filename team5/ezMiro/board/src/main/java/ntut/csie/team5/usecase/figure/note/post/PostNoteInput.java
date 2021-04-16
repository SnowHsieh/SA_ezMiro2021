package ntut.csie.team5.usecase.figure.note.post;

import ntut.csie.sslab.ddd.usecase.Input;

import java.awt.*;

public interface PostNoteInput extends Input {

    String getBoardId();

    void setBoardId(String boardId);

    Point getPosition();

    void setPosition(Point position);

    Color getColor();

    void setColor(Color color);
}
