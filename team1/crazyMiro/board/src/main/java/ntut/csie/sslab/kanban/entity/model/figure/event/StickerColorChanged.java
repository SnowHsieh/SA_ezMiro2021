package ntut.csie.sslab.kanban.entity.model.figure.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.kanban.usecase.figure.FigureRepository;

import java.util.Date;

public class StickerColorChanged extends DomainEvent {
    private String figureId;
    private String color;

    public StickerColorChanged(String figureId, String color) {
        super(DateProvider.now());
        this.figureId = figureId;
        this.color = color;
    }

    public String getFigureId() {
        return figureId;
    }

    public String getColor() {
        return color;
    }
}
