package ntut.csie.sslab.miro.usecase.figure.sticker.move;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.sslab.miro.entity.model.Coordinate;

public interface MoveStickerInput extends Input {

    void setFigureId(String stickerId);

    void setPosition(Coordinate position);

    void setUserId(String userId);

    String getFigureId();

    Coordinate getPosition();

    String getUserId();
}
