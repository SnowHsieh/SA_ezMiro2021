package ntut.csie.selab.adapter.gateway.repository.springboot.widget;

import ntut.csie.selab.adapter.gateway.repository.springboot.board.BoardData;

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

//    public CommittedWidgetData (BoardData board, StickyNoteData widget) {
//        this.board = board;
//        this.widget = widget;
//        this.id = new CommittedWidgetDataKey(board.getBoardId(), widget.getWidgetId());
//    }
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
