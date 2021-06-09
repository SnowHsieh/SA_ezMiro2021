package ntut.csie.selab.entity.model.widget.event;

import ntut.csie.selab.model.DomainEvent;

import java.util.Date;

public class LinkedStickyNoteIdDeleted extends DomainEvent {

    public LinkedStickyNoteIdDeleted(Date date) {
        super(date);
    }
}
