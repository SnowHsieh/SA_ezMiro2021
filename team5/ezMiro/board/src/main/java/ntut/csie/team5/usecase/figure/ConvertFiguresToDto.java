package ntut.csie.team5.usecase.figure;

import ntut.csie.team5.entity.model.figure.ConnectableFigure;
import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.entity.model.figure.line.Line;
import ntut.csie.team5.usecase.figure.line.ConvertLineToDto;

import java.util.ArrayList;
import java.util.List;

public class ConvertFiguresToDto {

    public static FigureDto transform(Figure figure) {
        if (figure instanceof ConnectableFigure) {
            return ConvertConnectableFigureToDto.transform(figure);
        } else if (figure instanceof Line) {
            return ConvertLineToDto.transform((Line) figure);
        }

        FigureDto dto = new FigureDto();
        dto.setBoardId(figure.getBoardId());
        dto.setFigureId(figure.getId());
        return dto;
    }

    public static List<FigureDto> transform(List<Figure> figures) {
        List<FigureDto> figureDtos = new ArrayList<>();
        for (Figure figure : figures) {
            figureDtos.add(transform(figure));
        }
        return figureDtos;
    }
}
