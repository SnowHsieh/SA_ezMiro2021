package ntut.csie.islab.miro.usecase.board;

import ntut.csie.islab.miro.figure.entity.model.figure.Figure;
import ntut.csie.islab.miro.figure.entity.model.figure.Style;
import ntut.csie.islab.miro.figure.usecase.figure.FigureDto;

import java.util.ArrayList;
import java.util.List;

public class ConvertFigureToDto {

    public static FigureDto transform(Figure figure) {
        FigureDto dto = new FigureDto();

        dto.setFigureId(figure.getFigureId());
        dto.setBoardId(figure.getBoardId());
        dto.setContent(figure.getContent());
        dto.setPosition(figure.getPosition());
        dto.setStyle(figure.getStyle());
        return dto;
    }

    public static List<FigureDto> transform(List<Figure> figureList) {
        List<FigureDto> figureDtos = new ArrayList<>();
        for(Figure figure : figureList) {
            figureDtos.add(transform(figure));
        }

        return figureDtos;
    }
}
