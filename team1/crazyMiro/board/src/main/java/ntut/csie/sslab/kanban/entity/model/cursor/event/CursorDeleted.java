package ntut.csie.sslab.kanban.entity.model.cursor.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;


public class CursorDeleted extends DomainEvent {
    private String cursorId;

    public CursorDeleted(String cursorId) {
        super(DateProvider.now());
        this.cursorId = cursorId;
    }

    public String getCursorId() {
        return cursorId;
    }
}
