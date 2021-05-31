package ntut.csie.selab.adapter.gateway.repository.springboot.widget;

import ntut.csie.selab.adapter.gateway.repository.springboot.board.BoardData;

import javax.persistence.*;
import javax.websocket.OnClose;

@Entity
public class CommittedWidgetData {

    @Id
    @Column(name="widget_id", nullable=false)
    private String widgetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", insertable = false, updatable = false)
    private BoardData board;

    @Column(name = "z_order")
    private int zOrder;

    public CommittedWidgetData() {

    }

    public CommittedWidgetData ( String widgetId, int zOrder) {
        this.widgetId = widgetId;
        this.zOrder = zOrder;
    }

    public String getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    public BoardData getBoard() {
        return board;
    }

    public void setBoard(BoardData board) {
        this.board = board;
    }

    public void setzOrder(int zOrder) {
        this.zOrder = zOrder;
    }

    public int getzOrder() {
        return zOrder;
    }
}
