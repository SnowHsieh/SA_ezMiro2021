package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure;

import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.Figure;
import ntut.csie.sslab.miro.entity.model.figure.FigureType;
import ntut.csie.sslab.miro.entity.model.figure.Sticker;

import java.util.ArrayList;
import java.util.List;

public class FigureMapper {

    public static Figure transformToDomain(FigureData figureData) {
        Figure figure = null;
        Coordinate position = new Coordinate(figureData.getX(), figureData.getY());
        if(figureData.getType().equals(FigureType.Sticker)){
            figure = new Sticker(figureData.getBoardId(), figureData.getFigureId(), figureData.getContent(), figureData.getWidth(), figureData.getLength(), figureData.getColor(), position);
            figure.clearDomainEvents();
        }
        return figure;
    }

    public static List<Figure> transformToDomain(List<FigureData> figureDatas) {
        List<Figure> figures = new ArrayList<>();
        for(FigureData figureData : figureDatas) {
            Figure figure = null;
            Coordinate position = new Coordinate(figureData.getX(), figureData.getY());
            if(figureData.getType().equals(FigureType.Sticker)){
                figure = new Sticker(figureData.getBoardId(), figureData.getFigureId(), figureData.getContent(), figureData.getWidth(), figureData.getLength(), figureData.getColor(), position);
                figure.clearDomainEvents();
            }
            figures.add(figure);
        }
        return figures;
    }

    public static FigureData transformToData(Figure figure) {
        return new FigureData(figure.getFigureId(),
                            figure.getBoardId(),
                            figure.getContent(),
                            figure.getWidth(),
                            figure.getLength(),
                            figure.getColor(),
                            figure.getPosition().getX(),
                            figure.getPosition().getY(),
                            figure.getType());
    }
}
