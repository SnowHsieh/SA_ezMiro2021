package ntut.csie.sslab.miro.adapter.gateway.eventbus.google;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.miro.entity.model.board.event.BoardEntered;
import ntut.csie.sslab.miro.entity.model.board.event.BoardLeft;
import ntut.csie.sslab.miro.usecase.eventhandler.NotifyCursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotifyCursorAdapter {

    private NotifyCursor notifyCursor;

    @Autowired
    public NotifyCursorAdapter(NotifyCursor notifyCursor) {
        this.notifyCursor = notifyCursor;
    }

    @Subscribe
    public void whenBoardEntered(BoardEntered boardEntered) {
        notifyCursor.handleBoardEntered(boardEntered);
    }


    @Subscribe
    public void whenBoardLeft(BoardLeft boardLeft) {
        notifyCursor.handleBoardLeft(boardLeft);
    }
}
