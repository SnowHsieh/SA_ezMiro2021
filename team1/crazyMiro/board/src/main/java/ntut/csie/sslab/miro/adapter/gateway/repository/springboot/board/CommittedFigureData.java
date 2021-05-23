package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="committed_figure")
public class CommittedFigureData {

    @Id
    @Column(name="figure_id")
    private String figureId;

    @Column(name="z_order")
    private int zOrder;

    public CommittedFigureData() {
    }

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
