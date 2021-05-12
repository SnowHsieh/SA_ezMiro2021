package ntut.csie.sslab.kanban.usecase.eventhandler;

import ntut.csie.sslab.kanban.entity.model.board.event.FigureCommitted;
import ntut.csie.sslab.kanban.entity.model.board.event.FigureUnCommitted;
import ntut.csie.sslab.kanban.entity.model.Coordinate;
import ntut.csie.sslab.kanban.entity.model.figure.event.StickerCreated;
import ntut.csie.sslab.kanban.entity.model.figure.event.StickerDeleted;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FigureEventBroadcastTest extends AbstractSpringBootJpaTest {

//    @Test
//    public void create_a_card_broadcasts_a_card_created_model_and_a_card_committed_model(){
//        String workflowId = createWorkflow(boardId, "workflowName", userId);
//        String firstRootStageId = createStage(workflowId);
//        fakeBoardSessionBroadcaster.clearDomainEventModels();
//
//        createCard(workflowId, firstRootStageId);
//
//        assertEquals(2, fakeBoardSessionBroadcaster.getRemoteDomainEvents().size());
//        assertEquals(CardEvents.CardCreated.class.getSimpleName(), fakeBoardSessionBroadcaster.getRemoteDomainEvents().get(0).getEventSimpleName());
//        assertEquals(WorkflowEvents.CardCommitted.class.getSimpleName(), fakeBoardSessionBroadcaster.getRemoteDomainEvents().get(1).getEventSimpleName());
//    }

    @Test
    public void create_a_sticker_broadcasts_a_sticker_created_model_and_a_figure_committed_model(){
        String boardId = createBoard(UUID.randomUUID().toString(), "boardName");
        Coordinate stickerPosition = new Coordinate(new Random().nextLong(), new Random().nextLong());
        eventListener.clearEventCount();

        String stickerId = createSticker(boardId, "ddd", 100, 200, "red", stickerPosition);

        assertEquals(2, eventListener.getEventCount());
        assertTrue(eventListener.getEvent(0) instanceof StickerCreated);
        assertTrue(eventListener.getEvent(1) instanceof FigureCommitted);
    }

    @Test
    public void delete_a_sticker_broadcasts_a_sticker_deleted_model_and_a_figure_uncommitted_model(){
        String boardId = createBoard(UUID.randomUUID().toString(), "boardName");
        Coordinate stickerPosition = new Coordinate(new Random().nextLong(), new Random().nextLong());
        String stickerId = createSticker(boardId, "ddd", 100, 200, "red", stickerPosition);
        eventListener.clearEventCount();

        deleteSticker(stickerId);

        assertEquals(2, eventListener.getEventCount());
        assertTrue(eventListener.getEvent(0) instanceof StickerDeleted);
        assertTrue(eventListener.getEvent(1) instanceof FigureUnCommitted);
    }


}
