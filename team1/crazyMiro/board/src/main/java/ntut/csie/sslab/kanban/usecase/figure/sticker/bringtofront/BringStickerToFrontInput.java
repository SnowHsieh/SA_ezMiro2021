package ntut.csie.sslab.kanban.usecase.figure.sticker.bringtofront;

import ntut.csie.sslab.ddd.usecase.Input;

public interface BringStickerToFrontInput extends Input {
    void setFigureId(String stickerId1);

    void setBoardId(String boardId1);

    String getBoardId();

    String getFigureId();
}
