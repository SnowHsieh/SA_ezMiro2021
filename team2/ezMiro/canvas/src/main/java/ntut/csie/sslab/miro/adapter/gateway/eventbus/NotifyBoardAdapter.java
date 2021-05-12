package ntut.csie.sslab.miro.adapter.gateway.eventbus;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.miro.entity.model.note.event.NoteBroughtToFront;
import ntut.csie.sslab.miro.entity.model.note.event.NoteCreated;
import ntut.csie.sslab.miro.entity.model.note.event.NoteDeleted;
import ntut.csie.sslab.miro.entity.model.note.event.NoteSentToBack;
import ntut.csie.sslab.miro.usecase.eventhandler.NotifyBoard;
import org.springframework.beans.factory.annotation.Autowired;

public class NotifyBoardAdapter {
    private NotifyBoard notifyBoard;

    @Autowired
    public NotifyBoardAdapter(NotifyBoard notifyBoard) {
        this.notifyBoard = notifyBoard;
    }

    @Subscribe
    public void whenNoteCreated(NoteCreated noteCreated) {
        notifyBoard.whenNoteCreated(noteCreated);
    }

    @Subscribe
    public void whenNoteBroughtToFront(NoteBroughtToFront noteBroughtToFront) {
        notifyBoard.whenNoteBroughtToFront(noteBroughtToFront);
    }

    @Subscribe
    public void whenNoteSentToBack(NoteSentToBack noteSentToBack) {
        notifyBoard.whenNoteSentToBack(noteSentToBack);
    }

    @Subscribe
    public void whenNoteDeleted(NoteDeleted noteDeleted) {
        notifyBoard.whenNoteDeleted(noteDeleted);
    }
}