package ntut.csie.selab.adapter.gateway.repository.springboot.widget;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CommittedWidgetDataKey implements Serializable {

    @Column(name = "board_id")
    String boardId;

    @Column(name = "widget_id")
    String widgetId;
}
