package ntut.csie.islab.miro.usecase.board.getboardcontent;

import ntut.csie.islab.miro.entity.model.figure.Figure;
import ntut.csie.islab.miro.usecase.textfigure.FigureDto;

import java.util.ArrayList;
import java.util.List;

public class ConvertFigureToDto {

    public static FigureDto transform(Figure figure) {
        FigureDto dto = new FigureDto();

        dto.setFigureId(figure.getFigureId());
        dto.setBoardId(figure.getBoardId());

        //line
        dto.setPositionList(figure.getPositionList());
        dto.setSrcArrowKind(figure.getSrcArrowKind());
        dto.setDestArrowKind(figure.getDestArrowKind());
        dto.setStrokeWidth(figure.getStrokeWidth());
        dto.setColor(figure.getColor());
        //textFigure
        dto.setContent(figure.getContent());
        dto.setPosition(figure.getPosition());
        dto.setStyle(figure.getStyle());
        return dto;
    }

    public static List<FigureDto> transform(List<Figure> FigureList) {
        List<FigureDto> figureDtos = new ArrayList<>();
        for (Figure figure : FigureList) {
            figureDtos.add(transform(figure));
        }

        return figureDtos;
    }
}
