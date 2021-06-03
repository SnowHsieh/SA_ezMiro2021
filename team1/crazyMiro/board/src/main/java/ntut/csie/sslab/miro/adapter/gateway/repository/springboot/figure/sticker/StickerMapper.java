package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.sticker;

import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.ConnectionFigure;
import ntut.csie.sslab.miro.entity.model.figure.FigureType;
import ntut.csie.sslab.miro.entity.model.figure.Sticker;

import java.util.ArrayList;
import java.util.List;

public class StickerMapper {

    public static ConnectionFigure transformToDomain(StickerData stickerData) {
        ConnectionFigure figure = null;
        Coordinate position = new Coordinate(stickerData.getX(), stickerData.getY());
        if(stickerData.getType().equals(FigureType.Sticker)){
            figure = new Sticker(stickerData.getBoardId(), stickerData.getFigureId(), stickerData.getContent(), stickerData.getWidth(), stickerData.getLength(), stickerData.getColor(), position);
            figure.clearDomainEvents();
        }
        return figure;
    }

    public static List<ConnectionFigure> transformToDomain(List<StickerData> stickerDatas) {
        List<ConnectionFigure> figures = new ArrayList<>();
        for(StickerData stickerData : stickerDatas) {
            ConnectionFigure figure = null;
            Coordinate position = new Coordinate(stickerData.getX(), stickerData.getY());
            if(stickerData.getType().equals(FigureType.Sticker)){
                figure = new Sticker(stickerData.getBoardId(), stickerData.getFigureId(), stickerData.getContent(), stickerData.getWidth(), stickerData.getLength(), stickerData.getColor(), position);
                figure.clearDomainEvents();
            }
            figures.add(figure);
        }
        return figures;
    }

    public static StickerData transformToData(ConnectionFigure figure) {
        return new StickerData(figure.getFigureId(),
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
