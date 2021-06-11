package ntut.csie.islab.miro.usecase.board.getboardcontent;

import ntut.csie.islab.miro.entity.model.figure.Figure;
import ntut.csie.islab.miro.usecase.figure.FigureDto;

import java.util.ArrayList;
import java.util.List;

public class ConvertFigureToDto {

    public static FigureDto transform(Figure figure) {
        FigureDto dto = new FigureDto();

        dto.setKind(figure.getKind().toString());
        dto.setFigureId(figure.getFigureId());
        dto.setBoardId(figure.getBoardId());

        //line
        dto.setPositionList(figure.getPositionList());
        if(figure.getSrcConnectableFigureId()!=null){
            dto.setSrcConnectableFigureId(figure.getSrcConnectableFigureId());
        }
        if(figure.getDestConnectableFigureId()!=null){
            dto.setDestConnectableFigureId(figure.getDestConnectableFigureId());
        }

        dto.setSrcArrowKind(figure.getSrcArrowKind());
        dto.setDestArrowKind(figure.getDestArrowKind());
        dto.setStrokeWidth(figure.getStrokeWidth());
        dto.setColor(figure.getColor());
        //connectableFigure
        dto.setContent(figure.getContent());
        dto.setPosition(figure.getPosition());
        dto.setWidth(figure.getWidth());
        dto.setHeight(figure.getHeight());
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
