package ntut.csie.sslab.kanban.entity.model.figure;

import ntut.csie.sslab.kanban.entity.model.board.event.BoardCreated;
import ntut.csie.sslab.kanban.entity.model.figure.event.StickerCreated;

public class Sticker extends Figure {
    public Sticker(String boardId, String figureId, String content, int size, String color, Coordinate position) {
        super(boardId, figureId, content, size, color, position);

        addDomainEvent(new StickerCreated(boardId, figureId, content, size, color, position));
    }

    @Override
    public FigureType getType() {
        return FigureType.Sticker;
    }


}
