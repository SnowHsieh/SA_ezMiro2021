package ntut.csie.sslab.kanban.usecase.figure.sticker.changesize;

import ntut.csie.sslab.ddd.usecase.Input;

public interface ChangeStickerSizeInput extends Input {
    void setFigureId(String stickerId);

    void setSize(int newSize);

    String getFigureId();

    int getSize();
}
