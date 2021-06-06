package ntut.csie.sslab.miro.usecase.figure;

import ntut.csie.sslab.miro.entity.model.figure.Figure;
import ntut.csie.sslab.miro.entity.model.figure.Sticker;

import java.util.ArrayList;
import java.util.List;

public class ConvertStickerToDto {
    public static List<ConnectionFigureDto> transform(List<Figure> stickers) {
        List<ConnectionFigureDto> connectionFigureDtos = new ArrayList<>();
        for (Figure each : stickers) {
            Sticker sticker = (Sticker)each;
            connectionFigureDtos.add(new ConnectionFigureDto(sticker.getFigureId(),
                    sticker.getContent(),
                    sticker.getWidth(),
                    sticker.getLength(),
                    sticker.getColor(),
                    sticker.getPosition()));
        }
        return connectionFigureDtos;
    }
}
