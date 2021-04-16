package ntut.csie.sslab.miro.application.springboot.web.config;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardUseCaseImpl;
import ntut.csie.sslab.miro.usecase.board.getcontent.GetBoardContentUseCase;
import ntut.csie.sslab.miro.usecase.board.getcontent.GetBoardContentUseCaseImpl;
import ntut.csie.sslab.miro.usecase.card.CardRepository;
import ntut.csie.sslab.miro.usecase.card.create.CreateCardUseCase;
import ntut.csie.sslab.miro.usecase.card.create.CreateCardUseCaseImpl;
import ntut.csie.sslab.miro.usecase.card.delete.DeleteCardUseCase;
import ntut.csie.sslab.miro.usecase.card.delete.DeleteCardUseCaseImpl;
import ntut.csie.sslab.miro.usecase.card.edit.deadline.ChangeCardDeadlineUseCase;
import ntut.csie.sslab.miro.usecase.card.edit.deadline.ChangeCardDeadlineUseCaseImpl;
import ntut.csie.sslab.miro.usecase.card.edit.description.ChangeCardDescriptionUseCase;
import ntut.csie.sslab.miro.usecase.card.edit.description.ChangeCardDescriptionUseCaseImpl;
import ntut.csie.sslab.miro.usecase.card.edit.estimate.ChangeCardEstimateUseCase;
import ntut.csie.sslab.miro.usecase.card.edit.estimate.ChangeCardEstimateUseCaseImpl;
import ntut.csie.sslab.miro.usecase.card.edit.note.ChangeCardNoteUseCase;
import ntut.csie.sslab.miro.usecase.card.edit.note.ChangeCardNoteUseCaseImpl;
import ntut.csie.sslab.miro.usecase.card.edit.type.ChangeCardTypeUseCase;
import ntut.csie.sslab.miro.usecase.card.edit.type.ChangeCardTypeUseCaseImpl;
import ntut.csie.sslab.miro.usecase.card.get.GetCardUseCase;
import ntut.csie.sslab.miro.usecase.card.get.GetCardUseCaseImpl;
import ntut.csie.sslab.miro.usecase.eventhandler.*;
import ntut.csie.sslab.miro.usecase.lane.get.GetLaneUseCase;
import ntut.csie.sslab.miro.usecase.lane.get.GetLaneUseCaseImpl;
import ntut.csie.sslab.miro.usecase.lane.rename.RenameLaneUseCase;
import ntut.csie.sslab.miro.usecase.lane.rename.RenameLaneUseCaseImpl;
import ntut.csie.sslab.miro.usecase.lane.stage.create.CreateStageUseCase;
import ntut.csie.sslab.miro.usecase.lane.stage.create.CreateStageUseCaseImpl;
import ntut.csie.sslab.miro.usecase.lane.swimLane.create.CreateSwimLaneUseCase;
import ntut.csie.sslab.miro.usecase.lane.swimLane.create.CreateSwimLaneUseCaseImpl;
import ntut.csie.sslab.miro.usecase.workflow.WorkflowRepository;
import ntut.csie.sslab.miro.usecase.workflow.create.CreateWorkflowUseCase;
import ntut.csie.sslab.miro.usecase.workflow.create.CreateWorkflowUseCaseImpl;
import ntut.csie.sslab.miro.usecase.workflow.get.GetWorkflowUseCase;
import ntut.csie.sslab.miro.usecase.workflow.get.GetWorkflowUseCaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;

@Configuration("KanbanUseCaseInjection")
public class UseCaseInjection {
    private BoardRepository boardRepository;
    private WorkflowRepository workflowRepository;
    private CardRepository cardRepository;
    private DomainEventBus eventBus;
    private ExecutorService executor;


    @Bean(name="createNotifyBoard")
    public NotifyBoard createNotifyBoard() {
        return new NotifyBoard(boardRepository, eventBus);
    }

    @Bean(name="createNotifyWorkflow")
    public NotifyWorkflow createNotifyWorkflow() {
        return new NotifyWorkflow(cardRepository, workflowRepository, eventBus);
    }


    @Bean(name="createBoardUseCase")
    public CreateBoardUseCase createBoardUseCase() {
        return new CreateBoardUseCaseImpl(boardRepository, eventBus);
    }


    @Bean(name="createWorkflowUseCase")
    public CreateWorkflowUseCase createWorkflowUseCase() {
        return new CreateWorkflowUseCaseImpl(workflowRepository, eventBus);
    }

    @Bean(name="getWorkflowUseCase")
    public GetWorkflowUseCase getWorkflowUseCase() {
        return new GetWorkflowUseCaseImpl(workflowRepository);
    }

    @Bean(name="createStageUseCase")
    public CreateStageUseCase getCreateStageUseCase() {
        return new CreateStageUseCaseImpl(workflowRepository, eventBus);
    }

    @Bean(name="createSwimLaneUseCase")
    public CreateSwimLaneUseCase getCreateSwimLaneUseCase() {
        return new CreateSwimLaneUseCaseImpl(workflowRepository, eventBus);
    }

    @Bean(name="getLaneUseCase")
    public GetLaneUseCase getGetLaneUseCase() {
        return new GetLaneUseCaseImpl(workflowRepository);
    }

    @Bean(name="getBoardContentUseCase")
    public GetBoardContentUseCase getBoardContentUseCase() {
        return new GetBoardContentUseCaseImpl(boardRepository, workflowRepository, cardRepository, eventBus);
    }

    @Bean(name="createCardUseCase")
    public CreateCardUseCase getCreateCardUseCase() {
        return new CreateCardUseCaseImpl(cardRepository, eventBus);
    }

    @Bean(name="deleteCardUseCase")
    public DeleteCardUseCase getDeleteCardUseCase() {
        return new DeleteCardUseCaseImpl(cardRepository, eventBus);
    }

    @Bean(name="changeCardDescriptionUseCase")
    public ChangeCardDescriptionUseCase getChangeCardDescriptionUseCase() {
        return new ChangeCardDescriptionUseCaseImpl(cardRepository, eventBus);
    }

    @Bean(name="changeCardNoteUseCase")
    public ChangeCardNoteUseCase getChangeCardNoteUseCase() {
        return new ChangeCardNoteUseCaseImpl(cardRepository, eventBus);
    }

    @Bean(name="changeCardEstimateUseCase")
    public ChangeCardEstimateUseCase getChangeCardEstimateUseCase() {
        return new ChangeCardEstimateUseCaseImpl(cardRepository, eventBus);
    }

    @Bean(name="changeCardDeadlineUseCase")
    public ChangeCardDeadlineUseCase getChangeCardDeadlineUseCase() {
        return new ChangeCardDeadlineUseCaseImpl(cardRepository, eventBus);
    }

    @Bean(name="changeCardTypeUseCase")
    public ChangeCardTypeUseCase getChangeCardTypeUseCase() {
        return new ChangeCardTypeUseCaseImpl(cardRepository, eventBus);
    }

    @Bean(name="getCardUseCase")
    public GetCardUseCase getGetCardUseCase() {
        return new GetCardUseCaseImpl(cardRepository);
    }


    @Bean(name="renameLaneUseCase")
    public RenameLaneUseCase getRenameLaneUseCase() {
        return new RenameLaneUseCaseImpl(workflowRepository, eventBus);
    }

    @Autowired
    public void setBoardRepository(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Autowired
    public void setWorkflowRepository(WorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    @Autowired
    public void setCardRepository(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }


    @Autowired
    public void setEventBus(DomainEventBus eventBus) { this.eventBus = eventBus; }


}
