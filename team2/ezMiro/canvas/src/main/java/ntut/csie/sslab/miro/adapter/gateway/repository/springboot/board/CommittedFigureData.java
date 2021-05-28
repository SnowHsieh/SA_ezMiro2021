package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="committed_figure")
public class CommittedFigureData {

    @Column(name="board_id", nullable = false)
    private String boardId;

    @Id
    @Column(name="figure_id")
    private String figureId;

    @Column(name="z_order")
    private int zOrder;

    public CommittedFigureData(){}

    public CommittedFigureData(String boardId, String figureId, int zOrder) {
        this();
        this.boardId = boardId;
        this.figureId = figureId;
        this.zOrder = zOrder;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getFigureId() {
        return figureId;
    }

    public void setFigureId(String figureId) {
        this.figureId = figureId;
    }

    public int getzOrder() {
        return zOrder;
    }

    public void setzOrder(int zOrder) {
        this.zOrder = zOrder;
    }
}
