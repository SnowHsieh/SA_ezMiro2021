package ntut.csie.team5.usecase.figure.connectable_figure.note.delete;

import ntut.csie.sslab.ddd.usecase.Input;

import java.awt.*;

public interface DeleteNoteInput extends Input {
    String getBoardId();

    void setBoardId(String boardId);

    Point getPosition();

    void setPosition(Point position);

    String getColor();

    void setColor(String color);

    String getNoteId();

    void setNoteId(String noteId);
}
