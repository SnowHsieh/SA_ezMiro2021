package ntut.csie.sslab.kanban.adapter.gateway.eventbus.google;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.kanban.entity.model.cursor.event.CursorCreated;
import ntut.csie.sslab.kanban.entity.model.cursor.event.CursorMoved;
import ntut.csie.sslab.kanban.entity.model.figure.event.StickerCreated;
import ntut.csie.sslab.kanban.entity.model.figure.event.StickerDeleted;
import ntut.csie.sslab.kanban.usecase.eventhandler.NotifyBoard;
import ntut.csie.sslab.kanban.usecase.eventhandler.NotifyBoardSessionBroadcaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotifyBoardAdapter {

    private NotifyBoard notifyBoard;
    private NotifyBoardSessionBroadcaster notifyBoardSessionBroadcaster;


    @Autowired
    public NotifyBoardAdapter(NotifyBoard notifyBoard, NotifyBoardSessionBroadcaster notifyBoardSessionBroadcaster) {
        this.notifyBoard = notifyBoard;
        this.notifyBoardSessionBroadcaster = notifyBoardSessionBroadcaster;
    }

    @Subscribe
    public void whenStickerCreated(StickerCreated stickerCreated) {
        notifyBoard.whenStickerCreated(stickerCreated);
    }


    @Subscribe
    public void whenStickerDeleted(StickerDeleted stickerDeleted) {
        notifyBoard.whenStickerDeleted(stickerDeleted);
    }

    @Subscribe
    public void whenCursorCreated(CursorCreated cursorCreated) {
        notifyBoardSessionBroadcaster.whenCursorCreated(cursorCreated);
    }

    @Subscribe
    public void whenCursorMoved(CursorMoved cursorMoved) {
        notifyBoardSessionBroadcaster.whenCursorMoved(cursorMoved);
    }

}
