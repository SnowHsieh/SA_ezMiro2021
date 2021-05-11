package ntut.csie.sslab.kanban.usecase.figure.sticker.move;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.sslab.kanban.entity.model.Coordinate;

public interface MoveStickerInput extends Input {

    void setFigureId(String stickerId);

    void setPosition(Coordinate position);

    String getFigureId();

    Coordinate getPosition();
}
