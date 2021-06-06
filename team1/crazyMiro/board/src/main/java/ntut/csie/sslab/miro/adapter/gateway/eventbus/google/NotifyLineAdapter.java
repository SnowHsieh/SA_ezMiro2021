package ntut.csie.sslab.miro.adapter.gateway.eventbus.google;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.miro.entity.model.figure.event.StickerDeleted;
import ntut.csie.sslab.miro.usecase.eventhandler.NotifyLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotifyLineAdapter {
    private NotifyLine notifyLine;

    @Autowired
    public NotifyLineAdapter (NotifyLine notifyLine) {this.notifyLine = notifyLine;};

    @Subscribe
    public void whenStickerDeleted(StickerDeleted stickerDeleted) {notifyLine.handleStickerDeleted(stickerDeleted);}
}
