package ntut.csie.sslab.miro.usecase.eventhandler;

import ntut.csie.sslab.miro.entity.model.board.event.FigureCommitted;
import ntut.csie.sslab.miro.entity.model.board.event.FigureUnCommitted;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.event.LineDeleted;
import ntut.csie.sslab.miro.entity.model.figure.event.StickerCreated;
import ntut.csie.sslab.miro.entity.model.figure.event.StickerDeleted;
import ntut.csie.sslab.miro.usecase.AbstractSpringBootJpaTest;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FigureEventBroadcastTest extends AbstractSpringBootJpaTest {

    @Test
    public void create_a_sticker_broadcasts_a_sticker_created_event_and_a_figure_committed_model(){
        String boardId = createBoard(UUID.randomUUID().toString(), "boardName");
        Coordinate stickerPosition = new Coordinate(new Random().nextLong(), new Random().nextLong());
        eventListener.clearEventCount();

        String stickerId = createSticker(boardId, "ddd", 100, 200, "red", stickerPosition);

        assertEquals(2, eventListener.getEventCount());
        assertTrue(eventListener.getEvent(0) instanceof StickerCreated);
        assertTrue(eventListener.getEvent(1) instanceof FigureCommitted);
    }

    @Test
    public void delete_a_sticker_broadcasts_a_sticker_deleted_event_and_a_figure_uncommitted_event(){
        String boardId = createBoard(UUID.randomUUID().toString(), "boardName");
        Coordinate stickerPosition = new Coordinate(new Random().nextLong(), new Random().nextLong());
        String stickerId = createSticker(boardId, "ddd", 100, 200, "red", stickerPosition);
        eventListener.clearEventCount();

        deleteSticker(stickerId);

        assertEquals(2, eventListener.getEventCount());
        assertTrue(eventListener.getEvent(0) instanceof StickerDeleted);
        assertTrue(eventListener.getEvent(1) instanceof FigureUnCommitted);
    }

    @Test
    public  void delete_a_sticker_broadcasts_a_sticker_deleted_event_and_a_line_deleted_event(){
        String boardId = createBoard(UUID.randomUUID().toString(), "boardName");
        Coordinate stickerPosition = new Coordinate(new Random().nextLong(), new Random().nextLong());
        String sourceStickerId = createSticker(boardId, "ddd", 100, 200, "red", stickerPosition);
        String targetStickerId = createSticker(boardId, "ddd", 100, 200, "red", stickerPosition);
        String lineID = createLine(boardId, sourceStickerId, targetStickerId, null, null);
        eventListener.clearEventCount();

        deleteSticker(sourceStickerId);

        assertEquals(3,eventListener.getEventCount());
        assertTrue(eventListener.getEvent(0) instanceof StickerDeleted);
        assertTrue(eventListener.getEvent(1) instanceof FigureUnCommitted);
        assertTrue(eventListener.getEvent(2) instanceof LineDeleted);
    }


}
