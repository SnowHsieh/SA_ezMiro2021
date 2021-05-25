package ntut.csie.selab.adapter.gateway.repository.springboot.widget;

import ntut.csie.selab.adapter.gateway.repository.springboot.board.BoardData;

import javax.persistence.*;

@Entity
//@Table(name = "committed_widget")
public class CommittedWidgetData {

    @EmbeddedId
    CommittedWidgetDataKey id;

    @ManyToOne
    @MapsId("boardId")
    @JoinColumn(name = "board_id")
    private BoardData board;

    @ManyToOne
    @MapsId("widgetId")
    @JoinColumn(name = "widget_id")
    private WidgetData widget;

    @Column(name = "z_order")
    private int zOrder;
}
