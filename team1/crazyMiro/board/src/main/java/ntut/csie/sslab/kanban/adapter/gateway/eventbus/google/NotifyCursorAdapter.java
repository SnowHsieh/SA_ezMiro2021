package ntut.csie.sslab.kanban.adapter.gateway.eventbus.google;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.kanban.entity.model.board.event.BoardEntered;
import ntut.csie.sslab.kanban.entity.model.board.event.BoardLeft;
import ntut.csie.sslab.kanban.usecase.eventhandler.NotifyCursor;
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
