package ntut.csie.sslab.miro.adapter.gateway.eventbus.google;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.miro.entity.model.figure.event.StickerCreated;
import ntut.csie.sslab.miro.entity.model.figure.event.StickerDeleted;
import ntut.csie.sslab.miro.usecase.eventhandler.NotifyBoard;
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
        notifyBoard.handleStickerCreated(stickerCreated);
    }

    @Subscribe
    public void whenStickerDeleted(StickerDeleted stickerDeleted) {
        notifyBoard.handleStickerDeleted(stickerDeleted);
    }

}
