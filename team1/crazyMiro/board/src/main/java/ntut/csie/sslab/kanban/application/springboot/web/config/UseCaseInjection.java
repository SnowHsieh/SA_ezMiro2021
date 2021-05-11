package ntut.csie.sslab.kanban.application.springboot.web.config;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.kanban.usecase.board.BoardRepository;
import ntut.csie.sslab.kanban.usecase.board.bringFigureToFront.BringFigureToFrontUseCase;
import ntut.csie.sslab.kanban.usecase.board.bringFigureToFront.BringFigureToFrontUseCaseImpl;
import ntut.csie.sslab.kanban.usecase.board.sendFigureToBack.SendFigureToBackUseCase;
import ntut.csie.sslab.kanban.usecase.board.sendFigureToBack.SendFigureToBackUseCaseImpl;
import ntut.csie.sslab.kanban.usecase.eventhandler.NotifyBoard;
import ntut.csie.sslab.kanban.usecase.figure.FigureRepository;
import ntut.csie.sslab.kanban.usecase.figure.sticker.changecolor.ChangeStickerColorUseCase;
import ntut.csie.sslab.kanban.usecase.figure.sticker.changecolor.ChangeStickerColorUseCaseImpl;
import ntut.csie.sslab.kanban.usecase.figure.sticker.changecontent.ChangeStickerContentUseCase;
import ntut.csie.sslab.kanban.usecase.figure.sticker.changecontent.ChangeStickerContentUseCaseImpl;
import ntut.csie.sslab.kanban.usecase.figure.sticker.changesize.ChangeStickerSizeUseCase;
import ntut.csie.sslab.kanban.usecase.figure.sticker.changesize.ChangeStickerSizeUseCaseImpl;
import ntut.csie.sslab.kanban.usecase.figure.sticker.create.CreateStickerUseCase;
import ntut.csie.sslab.kanban.usecase.figure.sticker.create.CreateStickerUseCaseImpl;
import ntut.csie.sslab.kanban.usecase.figure.sticker.delete.DeleteStickerUseCase;
import ntut.csie.sslab.kanban.usecase.figure.sticker.delete.DeleteStickerUseCaseImpl;
import ntut.csie.sslab.kanban.usecase.figure.sticker.move.MoveStickerUseCase;
import ntut.csie.sslab.kanban.usecase.figure.sticker.move.MoveStickerUseCaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration("KanbanUseCaseInjection")
public class UseCaseInjection {


    @Autowired
    private FigureRepository figureRepository;

    @Autowired
    private BoardRepository boardRepository;
//    private WorkflowRepository workflowRepository;
//    private CardRepository cardRepository;
    private DomainEventBus eventBus;
//    private ExecutorService executor;
//
//
    @Bean(name="createNotifyBoard")
    public NotifyBoard createNotifyBoard() {
        return new NotifyBoard(boardRepository, eventBus);
    }
//
//    @Bean(name="createNotifyWorkflow")
//    public NotifyWorkflow createNotifyWorkflow() {
//        return new NotifyWorkflow(cardRepository, workflowRepository, eventBus);
//    }
//
//
    @Bean(name="createStickerUseCase")
    public CreateStickerUseCase createStickerUseCase() {
        return new CreateStickerUseCaseImpl(figureRepository, eventBus);
    }

    @Bean(name="changeStickerContentUseCase")
    public ChangeStickerContentUseCase changeStickerContentUseCase() {
        return new ChangeStickerContentUseCaseImpl(figureRepository, eventBus);
    }

    @Bean(name="moveStickerUseCase")
    public MoveStickerUseCase moveStickerUseCase() {
        return new MoveStickerUseCaseImpl(figureRepository, eventBus);
    }

    @Bean(name="changeStickerSizeUseCase")
    public ChangeStickerSizeUseCase changeStickerSizeUseCase() {
        return new ChangeStickerSizeUseCaseImpl(figureRepository, eventBus);
    }

    @Bean(name="changeStickerColorUseCase")
    public ChangeStickerColorUseCase changeStickerColorUseCase() {
        return new ChangeStickerColorUseCaseImpl(figureRepository, eventBus);
    }

    @Bean(name="deleteStickerUseCase")
    public DeleteStickerUseCase deleteStickerUseCase() {
        return new DeleteStickerUseCaseImpl(figureRepository, eventBus);
    }


    @Bean(name="bringFigureToFrontUseCase")
    public BringFigureToFrontUseCase bringFigureToFrontUseCase() {
        return new BringFigureToFrontUseCaseImpl(boardRepository, eventBus);
    }

    @Bean(name="sendFigureToBackUseCase")
    public SendFigureToBackUseCase sendFigureToBackUseCase() {
        return new SendFigureToBackUseCaseImpl(boardRepository, eventBus);
    }


//
//
//    @Bean(name="createWorkflowUseCase")
//    public CreateWorkflowUseCase createWorkflowUseCase() {
//        return new CreateWorkflowUseCaseImpl(workflowRepository, eventBus);
//    }
//
//    @Bean(name="getWorkflowUseCase")
//    public GetWorkflowUseCase getWorkflowUseCase() {
//        return new GetWorkflowUseCaseImpl(workflowRepository);
//    }
//
//    @Bean(name="createStageUseCase")
//    public CreateStageUseCase getCreateStageUseCase() {
//        return new CreateStageUseCaseImpl(workflowRepository, eventBus);
//    }
//
//    @Bean(name="createSwimLaneUseCase")
//    public CreateSwimLaneUseCase getCreateSwimLaneUseCase() {
//        return new CreateSwimLaneUseCaseImpl(workflowRepository, eventBus);
//    }
//
//    @Bean(name="getLaneUseCase")
//    public GetLaneUseCase getGetLaneUseCase() {
//        return new GetLaneUseCaseImpl(workflowRepository);
//    }
//
//    @Bean(name="getBoardContentUseCase")
//    public GetBoardContentUseCase getBoardContentUseCase() {
//        return new GetBoardContentUseCaseImpl(boardRepository, workflowRepository, cardRepository, eventBus);
//    }
//
//    @Bean(name="createCardUseCase")
//    public CreateCardUseCase getCreateCardUseCase() {
//        return new CreateCardUseCaseImpl(cardRepository, eventBus);
//    }
//
//    @Bean(name="deleteCardUseCase")
//    public DeleteCardUseCase getDeleteCardUseCase() {
//        return new DeleteCardUseCaseImpl(cardRepository, eventBus);
//    }
//
//    @Bean(name="changeCardDescriptionUseCase")
//    public ChangeCardDescriptionUseCase getChangeCardDescriptionUseCase() {
//        return new ChangeCardDescriptionUseCaseImpl(cardRepository, eventBus);
//    }
//
//    @Bean(name="changeCardNoteUseCase")
//    public ChangeCardNoteUseCase getChangeCardNoteUseCase() {
//        return new ChangeCardNoteUseCaseImpl(cardRepository, eventBus);
//    }
//
//    @Bean(name="changeCardEstimateUseCase")
//    public ChangeCardEstimateUseCase getChangeCardEstimateUseCase() {
//        return new ChangeCardEstimateUseCaseImpl(cardRepository, eventBus);
//    }
//
//    @Bean(name="changeCardDeadlineUseCase")
//    public ChangeCardDeadlineUseCase getChangeCardDeadlineUseCase() {
//        return new ChangeCardDeadlineUseCaseImpl(cardRepository, eventBus);
//    }
//
//    @Bean(name="changeCardTypeUseCase")
//    public ChangeCardTypeUseCase getChangeCardTypeUseCase() {
//        return new ChangeCardTypeUseCaseImpl(cardRepository, eventBus);
//    }
//
//    @Bean(name="getCardUseCase")
//    public GetCardUseCase getGetCardUseCase() {
//        return new GetCardUseCaseImpl(cardRepository);
//    }
//
//
//    @Bean(name="renameLaneUseCase")
//    public RenameLaneUseCase getRenameLaneUseCase() {
//        return new RenameLaneUseCaseImpl(workflowRepository, eventBus);
//    }
//
//    @Autowired
//    public void setBoardRepository(BoardRepository boardRepository) {
//        this.boardRepository = boardRepository;
//    }
//
//    @Autowired
//    public void setWorkflowRepository(WorkflowRepository workflowRepository) {
//        this.workflowRepository = workflowRepository;
//    }
//
//    @Autowired
//    public void setCardRepository(CardRepository cardRepository) {
//        this.cardRepository = cardRepository;
//    }
//
//
    @Autowired
    public void setEventBus(DomainEventBus eventBus) { this.eventBus = eventBus; }


}
