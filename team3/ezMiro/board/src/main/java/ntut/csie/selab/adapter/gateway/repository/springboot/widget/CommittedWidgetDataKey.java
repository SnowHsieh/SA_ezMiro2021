package ntut.csie.selab.adapter.gateway.repository.springboot.widget;

import org.apache.commons.lang3.tuple.ImmutablePair;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CommittedWidgetDataKey implements Serializable {

    @Column(name = "board_id")
    String boardId;

    @Column(name = "widget_id")
    String widgetId;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof  CommittedWidgetDataKey)) {
            return false;
        }
        CommittedWidgetDataKey key = (CommittedWidgetDataKey)obj;
        return boardId.equals(key.boardId) && boardId.equals(key.widgetId);
    }

    @Override
    public int hashCode() {
        return new ImmutablePair<String, String>(boardId, widgetId).hashCode();
    }

    public CommittedWidgetDataKey() {

    }

    public CommittedWidgetDataKey(String boardId, String widgetId) {
        this.boardId = boardId;
        this.widgetId = widgetId;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getWidgetId() {
        return widgetId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }
}
