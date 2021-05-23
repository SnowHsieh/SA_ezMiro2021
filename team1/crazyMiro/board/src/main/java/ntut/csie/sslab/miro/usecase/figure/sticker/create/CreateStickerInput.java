package ntut.csie.sslab.miro.usecase.figure.sticker.create;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.sslab.miro.entity.model.Coordinate;

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