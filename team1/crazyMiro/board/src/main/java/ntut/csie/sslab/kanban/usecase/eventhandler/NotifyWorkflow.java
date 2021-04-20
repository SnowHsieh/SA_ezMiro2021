package ntut.csie.sslab.kanban.usecase.eventhandler;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.kanban.entity.model.card.Card;
import ntut.csie.sslab.kanban.entity.model.card.event.CardBlocked;
import ntut.csie.sslab.kanban.entity.model.card.event.CardUnblocked;
import ntut.csie.sslab.kanban.entity.model.workflow.Workflow;
import ntut.csie.sslab.kanban.entity.model.workflow.event.CardCommitted;
import ntut.csie.sslab.kanban.entity.model.workflow.event.CardMoved;
import ntut.csie.sslab.kanban.entity.model.workflow.event.CardUncommitted;
import ntut.csie.sslab.kanban.usecase.card.CardRepository;
import ntut.csie.sslab.kanban.usecase.workflow.WorkflowRepository;
import ntut.csie.sslab.kanban.entity.model.card.event.CardCreated;
import ntut.csie.sslab.kanban.entity.model.card.event.CardDeleted;

public class NotifyWorkflow {
    private CardRepository cardRepository;
    private WorkflowRepository workflowRepository;

    private DomainEventBus domainEventBus;

    public NotifyWorkflow(CardRepository cardRepository,
                          WorkflowRepository workflowRepository,
                          DomainEventBus domainEventBus) {
        this.cardRepository = cardRepository;
        this.workflowRepository = workflowRepository;
        this.domainEventBus = domainEventBus;
    }

    @Subscribe
    public void whenCardCreated(CardCreated cardCreated) {
        Workflow workflow = workflowRepository.findById(cardCreated.workflowId()).get();
        int order = workflow.getLaneById(cardCreated.laneId()).get().getCommittedCards().size();
        workflow.commitCardInLane(
                                cardCreated.cardId(),
                                cardCreated.laneId(),
                                order,
                                cardCreated.userId(),
                                cardCreated.username(),
                                cardCreated.boardId());
        workflowRepository.save(workflow);

        domainEventBus.postAll(workflow);
    }

    @Subscribe
    public void whenCardDeleted(CardDeleted cardDeleted) {
        Workflow workflow = workflowRepository.findById(cardDeleted.workflowId()).get();
        workflow.uncommitCardInLane(
                cardDeleted.cardId(),
                cardDeleted.laneId(),
                cardDeleted.userId(),
                cardDeleted.username(),
                cardDeleted.boardId()
        );

        workflowRepository.save(workflow);
        domainEventBus.postAll(workflow);
    }

    @Subscribe
    public void whenCardMoved(CardMoved cardMoved) {
        Workflow workflow = workflowRepository.findById(cardMoved.workflowId()).get();
        workflow.uncommitCardInLane(cardMoved.cardId(), cardMoved.originalLaneId(), cardMoved.userId(), cardMoved.username(), cardMoved.boardId());
        workflow.commitCardInLane(cardMoved.cardId(), cardMoved.newLaneId(), cardMoved.order(), cardMoved.userId(), cardMoved.username(), cardMoved.boardId());

        workflowRepository.save(workflow);
        domainEventBus.postAll(workflow);
    }

//    @Subscribe
//    public void handleEvent(CardCommitted cardCommitted) {
//        Card card= cardRepository.findById(cardCommitted.cardId()).get();
//        card.setLaneId(cardCommitted.newLaneId());
//        cardRepository.save(card);
//    }

}
