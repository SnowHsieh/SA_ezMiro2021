package ntut.csie.sslab.miro.usecase.figure.sticker.changecolor;

import ntut.csie.sslab.ddd.usecase.Input;

public interface ChangeStickerColorInput extends Input {
    void setFigureId(String stickerId);

    void setColor(String newColor);

    String getFigureId();

    String getColor();
}
