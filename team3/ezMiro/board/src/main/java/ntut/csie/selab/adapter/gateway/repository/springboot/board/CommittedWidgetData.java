package ntut.csie.selab.adapter.gateway.repository.springboot.board;

import javax.persistence.*;

@Entity
public class CommittedWidgetData {

    @EmbeddedId
    CommittedWidgetDataKey id;

    @ManyToOne
    @MapsId("boardId")
    @JoinColumn(name = "board_id")
    private BoardData board;

    @Column(name = "z_order")
    private int zOrder;

    public CommittedWidgetData() {

    }

    public CommittedWidgetData (String boardId, String widgetId, int zOrder) {
        this.board = new BoardData(boardId);
        this.id = new CommittedWidgetDataKey(boardId, widgetId);
        this.zOrder = zOrder;
    }

    public CommittedWidgetDataKey getId() {
        return id;
    }

    public int getzOrder() {
        return zOrder;
    }


}
