package ntut.csie.sslab.miro.usecase.figure;

import ntut.csie.sslab.miro.entity.model.figure.Figure;
import ntut.csie.sslab.miro.entity.model.figure.Sticker;

import java.util.ArrayList;
import java.util.List;

public class ConvertStickerToDto {
    public static List<FigureDto> transform(List<Figure> stickers) {
        List<FigureDto> figureDtos = new ArrayList<>();
        for (Figure each : stickers) {
            Sticker sticker = (Sticker)each;
            figureDtos.add(new FigureDto(sticker.getFigureId(),
                    sticker.getContent(),
                    sticker.getWidth(),
                    sticker.getLength(),
                    sticker.getColor(),
                    sticker.getPosition()));
        }
        return figureDtos;
    }
}
