package ntut.csie.sslab.miro.adapter.gateway.eventbus;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.miro.entity.model.figure.connectablefigure.note.event.NoteEvents;
import ntut.csie.sslab.miro.usecase.eventhandler.NotifyFigure;
import org.springframework.beans.factory.annotation.Autowired;

public class NotifyFigureAdapter {
    private NotifyFigure notifyFigure;

    @Autowired
    public NotifyFigureAdapter(NotifyFigure notifyFigure) {
        this.notifyFigure = notifyFigure;
    }

    @Subscribe
    public void whenNoteDeleted(NoteEvents.NoteDeleted noteDeleted) {
        notifyFigure.whenNoteDeleted(noteDeleted);
    }
}