package ntut.csie.sslab.kanban.adapter.gateway.eventbus.google;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.kanban.entity.model.figure.event.StickerCreated;
import ntut.csie.sslab.kanban.usecase.eventhandler.NotifyBoard;
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
    public void whenStickerCreated(StickerCreated stickerCreated) {
        notifyBoard.whenStickerCreated(stickerCreated);
    }

}
