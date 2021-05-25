package ntut.csie.sslab.miro.usecase.figure.sticker.changecontent;

import ntut.csie.sslab.ddd.usecase.Input;

public interface ChangeStickerContentInput extends Input {
    void setFigureId(String stickerId);

    String getFigureId();

    void setContent(String content);

    String getContent();

}
