package ntut.csie.sslab.kanban.entity.model.card;


import ntut.csie.sslab.kanban.entity.model.card.event.*;
import org.junit.jupiter.api.*;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CardTest {

    private String userId = "userId";
    private String username = "username";
    private String boardId = "boardId";
    private String tagId = "tagId";
    private String laneId = "laneId";
    private String workflowId = "workflowId";
    private String description = "description";
    private String deadline = "2019-12-12";
    private String notes = "notes";
    private String estimate = "xxl";
    private CardType cardType = CardType.General;
    private Card card;
    private SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");

    @BeforeEach
    public void setUp() {
        should_publish_card_created_domain_event_when_create_card();
    }

    private void should_publish_card_created_domain_event_when_create_card() {
        card = CardBuilder.newInstance()
                .userId(userId)
                .workflowId(workflowId)
                .laneId(laneId)
                .description(description)
                .deadline(deadline)
                .notes(notes)
                .estimate(estimate)
                .type(CardType.General)
                .username(username)
                .boardId(boardId)
                .build();

        assertEquals(userId, card.getUserId());
        assertEquals(boardId, card.getBoardId());
        assertEquals(laneId, card.getLaneId());
        assertEquals(description, card.getDescription());
        assertEquals(deadline, card.getDeadline());
        assertEquals(notes, card.getNotes());
        assertEquals(estimate, card.getEstimate());
        assertEquals(cardType, card.getType());

        assertEquals(1, card.getDomainEvents().size());
        assertEquals(CardCreated.class,card.getDomainEvents().get(0).getClass());
        assertEquals(laneId, ((CardCreated)card.getDomainEvents().get(0)).laneId());
        assertEquals(workflowId, ((CardCreated)card.getDomainEvents().get(0)).workflowId());
        assertEquals(card.getCardId(), ((CardCreated)card.getDomainEvents().get(0)).cardId());
        assertEquals(card.getDescription(), ((CardCreated)card.getDomainEvents().get(0)).description());
        assertEquals(card.getType().toString(), ((CardCreated)card.getDomainEvents().get(0)).type());
        assertEquals(card.getEstimate(), ((CardCreated)card.getDomainEvents().get(0)).estimate());
        assertEquals(card.getNotes(), ((CardCreated)card.getDomainEvents().get(0)).notes());
        assertEquals(card.getDeadline(), ((CardCreated)card.getDomainEvents().get(0)).deadline());
        assertEquals(userId, ((CardCreated)card.getDomainEvents().get(0)).userId());
        assertEquals(username, ((CardCreated)card.getDomainEvents().get(0)).username());
        assertEquals(boardId, ((CardCreated)card.getDomainEvents().get(0)).boardId());
        
        card.clearDomainEvents();
    }

    @Test
    public void should_publish_card_deleted_domain_event_when_delete_card() {

        String cardId = card.getCardId();
        String description = card.getDescription();

        card.markAsRemoved(workflowId, laneId, userId, username, boardId);
        assertEquals(1, card.getDomainEvents().size());
        assertEquals(CardDeleted.class, card.getDomainEvents().get(0).getClass());
        CardDeleted cardDeleted = (CardDeleted) card.getDomainEvents().get(0);
        assertEquals(cardId, cardDeleted.cardId());
        assertEquals(workflowId, cardDeleted.workflowId());
        assertEquals(laneId, cardDeleted.laneId());
        assertEquals(description, cardDeleted.description());
        assertEquals(userId, cardDeleted.userId());
        assertEquals(username, cardDeleted.username());
        assertEquals(boardId, cardDeleted.boardId());
    }

    @Test
    public void should_publish_card_description_changed_domain_event_when_change_card_description() {
        String newDescription = "newDescription";
        String oldDescription = card.getDescription();
        card.changeDescription(newDescription, userId, username, boardId);
        assertEquals(newDescription, card.getDescription());

        assertEquals(1, card.getDomainEvents().size());
        assertEquals(CardDescriptionChanged.class, card.getDomainEvents().get(0).getClass());
        CardDescriptionChanged cardDescriptionChanged = (CardDescriptionChanged) card.getDomainEvents().get(0);
        assertEquals(card.getCardId(), cardDescriptionChanged.cardId());
        assertEquals(card.getDescription(), cardDescriptionChanged.newDescription());
        assertEquals(oldDescription, cardDescriptionChanged.oldDescription());
        assertEquals(userId, cardDescriptionChanged.userId());
        assertEquals(username, cardDescriptionChanged.username());
        assertEquals(boardId, cardDescriptionChanged.boardId());
    }

    @Test
    public void should_publish_card_deadline_changed_domain_event_when_change_card_deadline() {
        String newDeadline = "2020-12-12";
        String oldDeadline = card.getDeadline();
        card.changeDeadline(newDeadline, userId, username, boardId);
        assertEquals(newDeadline, card.getDeadline());

        assertEquals(1, card.getDomainEvents().size());
        assertEquals(CardDeadlineChanged.class, card.getDomainEvents().get(0).getClass());
        CardDeadlineChanged cardDeadlineChanged = (CardDeadlineChanged) card.getDomainEvents().get(0);
        assertEquals(card.getCardId(), cardDeadlineChanged.cardId());
        assertEquals(card.getDeadline(), cardDeadlineChanged.newDeadline());
        assertEquals(oldDeadline, cardDeadlineChanged.oldDeadline());
        assertEquals(newDeadline, cardDeadlineChanged.newDeadline());
        assertEquals(userId, cardDeadlineChanged.userId());
        assertEquals(username, cardDeadlineChanged.username());
        assertEquals(boardId, cardDeadlineChanged.boardId());

    }

    @Test
    public void should_publish_card_estimate_changed_domain_event_when_change_card_estimate() {
        String newEstimate = "13";
        String oldEstimate = card.getEstimate();
        card.changeEstimate(newEstimate, userId, username, boardId);
        assertEquals(newEstimate, card.getEstimate());

        assertEquals(1, card.getDomainEvents().size());
        assertEquals(CardEstimateChanged.class, card.getDomainEvents().get(0).getClass());
        CardEstimateChanged cardEstimateChanged = (CardEstimateChanged) card.getDomainEvents().get(0);
        assertEquals(card.getCardId(), cardEstimateChanged.cardId());
        assertEquals(card.getEstimate(), cardEstimateChanged.newEstimate());
        assertEquals(oldEstimate, cardEstimateChanged.oldEstimate());
        assertEquals(userId, cardEstimateChanged.userId());
        assertEquals(username, cardEstimateChanged.username());
        assertEquals(boardId, cardEstimateChanged.boardId());
    }

    @Test
    public void should_publish_card_note_changed_domain_event_when_change_card_note() {
        String newNotes = "newNotes";
        String oldNotes = card.getNotes();
        card.changeNotes(newNotes, userId, username, boardId);
        assertEquals(newNotes, card.getNotes());

        assertEquals(1, card.getDomainEvents().size());
        assertEquals(CardNoteChanged.class, card.getDomainEvents().get(0).getClass());
        CardNoteChanged cardNoteChanged = (CardNoteChanged) card.getDomainEvents().get(0);
        assertEquals(card.getCardId(), cardNoteChanged.cardId());
        assertEquals(newNotes, cardNoteChanged.newNote());
        assertEquals(oldNotes, cardNoteChanged.oldNote());
        assertEquals(userId, cardNoteChanged.userId());
        assertEquals(username, cardNoteChanged.username());
        assertEquals(boardId, cardNoteChanged.boardId());
    }

}
