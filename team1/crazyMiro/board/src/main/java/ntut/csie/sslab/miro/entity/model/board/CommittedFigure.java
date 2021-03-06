package ntut.csie.sslab.miro.entity.model.board;

import ntut.csie.sslab.ddd.model.ValueObject;

public class CommittedFigure extends ValueObject {
    private final String figureId;
    private final int zOrder;

    public CommittedFigure(String figureId, int zOrder) {
        this.figureId = figureId;
        this.zOrder = zOrder;
    }

    public String getFigureId() {
        return figureId;
    }

    public int getZOrder() {
        return zOrder;
    }

}
