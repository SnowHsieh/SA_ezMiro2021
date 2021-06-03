package ntut.csie.sslab.miro.adapter.gateway.eventbus;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.miro.entity.model.figure.connectablefigure.note.event.NoteEvents;
import ntut.csie.sslab.miro.entity.model.figure.line.event.LineEvents;
import ntut.csie.sslab.miro.usecase.eventhandler.NotifyBoard;
import org.springframework.beans.factory.annotation.Autowired;

public class NotifyBoardAdapter {
    private NotifyBoard notifyBoard;

    @Autowired
    public NotifyBoardAdapter(NotifyBoard notifyBoard) {
        this.notifyBoard = notifyBoard;
    }

    @Subscribe
    public void whenNoteCreated(NoteEvents.NoteCreated noteCreated) {
        notifyBoard.whenNoteCreated(noteCreated);
    }

    @Subscribe
    public void whenNoteBroughtToFront(NoteEvents.NoteBroughtToFront noteBroughtToFront) {
        notifyBoard.whenNoteBroughtToFront(noteBroughtToFront);
    }

    @Subscribe
    public void whenNoteSentToBack(NoteEvents.NoteSentToBack noteSentToBack) {
        notifyBoard.whenNoteSentToBack(noteSentToBack);
    }

    @Subscribe
    public void whenNoteDeleted(NoteEvents.NoteDeleted noteDeleted) {
        notifyBoard.whenNoteDeleted(noteDeleted);
    }

    @Subscribe
    public void whenLineCreated(LineEvents.LineCreated lineCreated) {
        notifyBoard.whenLineCreated(lineCreated);
    }

    @Subscribe
    public void whenLineDeleted(LineEvents.LineDeleted lineDeleted) {
        notifyBoard.whenLineDeleted(lineDeleted);
    }
}