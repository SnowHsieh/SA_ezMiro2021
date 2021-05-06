package ntut.csie.sslab.kanban.entity.model.board;

public class CommittedFigure {
    private String figureId;
    private int zOrder;

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
