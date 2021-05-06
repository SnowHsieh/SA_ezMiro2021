package ntut.csie.sslab.kanban.usecase.figure;

import ntut.csie.sslab.kanban.entity.model.figure.Figure;

import java.util.ArrayList;
import java.util.List;

public class ConvertStickerToDto {
    public static List<FigureDto> transform(List<Figure> stickers) {
        List<FigureDto> figureDtos = new ArrayList<>();
        for (Figure each : stickers) {
            figureDtos.add(new FigureDto(each.getFigureId(),
                    each.getContent(),
                    each.getWidth(),
                    each.getLength(),
                    each.getColor(),
                    each.getPosition()));
        }
        return figureDtos;
    }
}
