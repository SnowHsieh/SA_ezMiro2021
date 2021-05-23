package ntut.csie.sslab.miro.usecase.figure.sticker.delete;

import ntut.csie.sslab.ddd.usecase.Input;

public interface DeleteStickerInput extends Input {

    void setFigureId(String stickerId);

    String getFigureId();

    void setBoardId(String boardId);

    String getBoardId();
}
