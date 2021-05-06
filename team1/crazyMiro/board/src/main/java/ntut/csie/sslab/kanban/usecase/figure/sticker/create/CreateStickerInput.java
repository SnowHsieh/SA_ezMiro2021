package ntut.csie.sslab.kanban.usecase.figure.sticker.create;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.sslab.kanban.entity.model.figure.Coordinate;

public interface CreateStickerInput extends Input {
    void setBoardId(String boardId);

    String getBoardId();

    void setContent(String content);

    String getContent();

    void setWidth(int width);

    int getWidth();

    void setColor(String color);

    String getColor();

    void setPosition(Coordinate position);

    Coordinate getPosition();

    void setLength(int length);

    int getLength();
}