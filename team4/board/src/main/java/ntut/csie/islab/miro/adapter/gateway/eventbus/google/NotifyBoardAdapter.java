package ntut.csie.islab.miro.adapter.gateway.eventbus.google;

import com.google.common.eventbus.Subscribe;
import ntut.csie.islab.miro.entity.model.figure.line.event.LineCreatedDomainEvent;
import ntut.csie.islab.miro.entity.model.figure.line.event.LineDeletedDomainEvent;
import ntut.csie.islab.miro.entity.model.figure.textfigure.stickynote.event.StickyNoteCreatedDomainEvent;
import ntut.csie.islab.miro.entity.model.figure.textfigure.stickynote.event.StickyNoteDeleteDomainEvent;
import ntut.csie.islab.miro.usecase.eventhandler.NotifyBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotifyBoardAdapter {
    private NotifyBoard notifyBoard;

    @Autowired
    public NotifyBoardAdapter(NotifyBoard notifyBoard) {
        this.notifyBoard = notifyBoard;
    }

    @Subscribe
    public void whenFigureCreated(StickyNoteCreatedDomainEvent stickyNoteCreatedDomainEvent) {
        notifyBoard.whenFigureCreated(stickyNoteCreatedDomainEvent);
    }

    @Subscribe
    public void whenFigureCreated(LineCreatedDomainEvent lineCreatedDomainEvent) {
        notifyBoard.whenFigureCreated(lineCreatedDomainEvent);
    }

    @Subscribe
    public void whenFigureDeleted(StickyNoteDeleteDomainEvent stickyNoteDeleteDomainEvent) {
        notifyBoard.whenFigureDeleted(stickyNoteDeleteDomainEvent);
    }

    @Subscribe
    public void whenFigureDeleted(LineDeletedDomainEvent lineDeletedDomainEvent) {
        notifyBoard.whenFigureDeleted(lineDeletedDomainEvent);
    }

}
