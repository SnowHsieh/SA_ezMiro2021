package ntut.csie.sslab.kanban.entity.model.figure;

import ntut.csie.sslab.kanban.entity.model.figure.event.*;

public class Sticker extends Figure {
    public Sticker(String boardId, String figureId, String content, int width, int length, String color, Coordinate position) {
        super(boardId, figureId, content, width, length, color, position);
        addDomainEvent(new StickerCreated(boardId, figureId, content, width, length, color, position));
    }

    @Override
    public FigureType getType() {
        return FigureType.Sticker;
    }

    public void changeContent(String content){
        setContent(content);
        addDomainEvent(new StickerContentChanged(this.getFigureId(), content));
    }

    public void move(Coordinate position) {
        setPosition(position);
        addDomainEvent(new StickerMoved(this.getFigureId(), position));
    }

    public void changeSize(int width, int length) {
        setWidth(width);
        setLength(length);
        addDomainEvent(new StickerSizeChanged(this.getFigureId(), width, length));
    }

    public void changeColor(String color) {
        setColor(color);
        addDomainEvent(new StickerColorChanged(this.getFigureId(), color));
    }

    public void deleteSticker() {
        markAsDelete();
        addDomainEvent(new StickerDeleted(this.getFigureId()));
    }
}
