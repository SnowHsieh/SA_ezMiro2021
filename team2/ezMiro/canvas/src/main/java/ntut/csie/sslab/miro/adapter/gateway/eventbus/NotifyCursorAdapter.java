package ntut.csie.sslab.miro.adapter.gateway.eventbus;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.miro.entity.model.board.event.BoardEvents;
import ntut.csie.sslab.miro.usecase.eventhandler.NotifyCursor;
import org.springframework.beans.factory.annotation.Autowired;

public class NotifyCursorAdapter {
    private NotifyCursor notifyCursor;

    @Autowired
    public NotifyCursorAdapter(NotifyCursor notifyCursor) {
        this.notifyCursor = notifyCursor;
    }

    @Subscribe
    public void whenBoardEntered(BoardEvents.BoardEntered boardEntered) {
        notifyCursor.whenBoardEntered(boardEntered);
    }

    @Subscribe
    public void whenBoardLeft(BoardEvents.BoardLeft boardLeft) {
        notifyCursor.whenBoardLeft(boardLeft);
    }
}