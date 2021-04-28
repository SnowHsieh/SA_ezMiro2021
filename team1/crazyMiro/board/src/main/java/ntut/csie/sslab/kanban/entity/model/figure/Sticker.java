package ntut.csie.sslab.kanban.entity.model.figure;

import ntut.csie.sslab.kanban.entity.model.board.event.BoardCreated;
import ntut.csie.sslab.kanban.entity.model.figure.event.StickerContentChanged;
import ntut.csie.sslab.kanban.entity.model.figure.event.StickerCreated;
import ntut.csie.sslab.kanban.entity.model.figure.event.StickerMoved;

public class Sticker extends Figure {
    public Sticker(String boardId, String figureId, String content, int size, String color, Coordinate position) {
        super(boardId, figureId, content, size, color, position);

        addDomainEvent(new StickerCreated(boardId, figureId, content, size, color, position));
    }

    @Override
    public FigureType getType() {
        return FigureType.Sticker;
    }

    public void changeContent(String content){
        setContent(content);
        addDomainEvent(new StickerContentChanged(this.getFigureId(), content));
    }

    @Override
    public void move(Coordinate position) {
        setPosition(position);
        addDomainEvent(new StickerMoved(this.getFigureId(), position));
    }
}
