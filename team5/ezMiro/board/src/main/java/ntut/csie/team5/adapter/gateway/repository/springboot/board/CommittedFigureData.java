package ntut.csie.team5.adapter.gateway.repository.springboot.board;

import javax.persistence.*;

@Entity
@Table(name = "committed_figure")
public class CommittedFigureData {

    @Id
    @Column(name = "figure_id")
    private String figureId;

    @Column(name = "board_id")
    private String boardId;

    @Column(name = "z_order")
    private int zOrder;

    public CommittedFigureData() {
    }

    public CommittedFigureData(String boardId, String figureId, int zOrder) {
        this.boardId = boardId;
        this.figureId = figureId;
        this.zOrder = zOrder;
    }

    public String getFigureId() {
        return figureId;
    }

    public void setFigureId(String figureId) {
        this.figureId = figureId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public int getZOrder() {
        return zOrder;
    }

    public void setZOrder(int zOrder) {
        this.zOrder = zOrder;
    }
}
