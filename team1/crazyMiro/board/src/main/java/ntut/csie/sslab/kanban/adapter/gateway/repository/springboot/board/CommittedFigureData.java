package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.board;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="committed_figure")
public class CommittedFigureData {

    @Id
    @Column(name="figure_id")
    private String figureId;

    @Column(name="z_order")
    private int zOrder;

    public CommittedFigureData(String figureId, int zOrder) {
        this.figureId = figureId;
        this.zOrder = zOrder;
    }

    public String getFigureId() {
        return figureId;
    }

    public int getzOrder() {
        return zOrder;
    }
}
