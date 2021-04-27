package ntut.csie.sslab.kanban.usecase.figure.sticker.create;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.sslab.kanban.entity.model.figure.Coordinate;

public interface CreateStickerInput extends Input {
    void setBoardId(String boardId);

    String getBoardId();

    void setContent(String content);

    String getContent();

    void setSize(int size);

    int getSize();

    void setColor(String color);

    String getColor();

    void setPosition(Coordinate position);

    Coordinate getPosition();
}