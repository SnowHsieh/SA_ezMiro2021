package ntut.csie.sslab.kanban.usecase.figure.sticker.changesize;

import ntut.csie.sslab.ddd.usecase.Input;

public interface ChangeStickerSizeInput extends Input {
    String getFigureId();

    void setFigureId(String stickerId);

    int getWidth();

    void setWidth(int newSize);

    int getLength();

    void setLength(int length);
}
