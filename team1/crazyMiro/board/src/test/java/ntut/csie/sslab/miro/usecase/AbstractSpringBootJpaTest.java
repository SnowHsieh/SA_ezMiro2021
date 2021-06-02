package ntut.csie.sslab.miro.usecase;
//
//import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
//import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
//import ntut.csie.sslab.kanban.adapter.gateway.eventbus.google.NotifyBoardAdapter;
//import ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.board.BoardRepositoryImpl;
//import ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.board.BoardRepositoryPeer;
//import ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.card.CardRepositoryImpl;
//import ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.card.CardRepositoryPeer;
//import ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.workflow.WorkflowRepositoryImpl;
//import ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.workflow.WorkflowRepositoryPeer;
//import ntut.csie.sslab.ddd.model.DomainEventBus;
//import ntut.csie.sslab.kanban.entity.model.card.Card;
//import ntut.csie.sslab.kanban.usecase.board.getcontent.GetBoardContentUseCase;
//import ntut.csie.sslab.kanban.usecase.board.getcontent.GetBoardContentUseCaseImpl;
//import ntut.csie.sslab.kanban.usecase.card.CardRepository;
//import ntut.csie.sslab.kanban.usecase.card.create.CreateCardInput;
//import ntut.csie.sslab.kanban.usecase.card.create.CreateCardUseCase;
//import ntut.csie.sslab.kanban.usecase.card.create.CreateCardUseCaseImpl;
//import ntut.csie.sslab.kanban.usecase.card.delete.DeleteCardInput;
//import ntut.csie.sslab.kanban.usecase.card.delete.DeleteCardUseCase;
//import ntut.csie.sslab.kanban.usecase.card.delete.DeleteCardUseCaseImpl;
//import ntut.csie.sslab.kanban.usecase.card.edit.deadline.ChangeCardDeadlineInput;
//import ntut.csie.sslab.kanban.usecase.card.edit.deadline.ChangeCardDeadlineUseCase;
//import ntut.csie.sslab.kanban.usecase.card.edit.deadline.ChangeCardDeadlineUseCaseImpl;
//import ntut.csie.sslab.kanban.usecase.card.edit.description.ChangeCardDescriptionInput;
//import ntut.csie.sslab.kanban.usecase.card.edit.description.ChangeCardDescriptionUseCase;
//import ntut.csie.sslab.kanban.usecase.card.edit.description.ChangeCardDescriptionUseCaseImpl;
//import ntut.csie.sslab.kanban.usecase.card.edit.estimate.ChangeCardEstimateInput;
//import ntut.csie.sslab.kanban.usecase.card.edit.estimate.ChangeCardEstimateUseCase;
//import ntut.csie.sslab.kanban.usecase.card.edit.estimate.ChangeCardEstimateUseCaseImpl;
//import ntut.csie.sslab.kanban.usecase.card.edit.note.ChangeCardNoteUseCase;
//import ntut.csie.sslab.kanban.usecase.card.edit.note.ChangeCardNoteUseCaseImpl;
//import ntut.csie.sslab.kanban.usecase.card.edit.note.ChangeCardNoteInput;
//import ntut.csie.sslab.kanban.usecase.card.edit.type.ChangeCardTypeInput;
//import ntut.csie.sslab.kanban.usecase.card.edit.type.ChangeCardTypeUseCase;
//import ntut.csie.sslab.kanban.usecase.card.edit.type.ChangeCardTypeUseCaseImpl;
//import ntut.csie.sslab.kanban.usecase.card.get.GetCardUseCase;
//import ntut.csie.sslab.kanban.usecase.card.get.GetCardUseCaseImpl;
//import ntut.csie.sslab.kanban.usecase.lane.rename.RenameLaneInput;
//import ntut.csie.sslab.kanban.usecase.lane.rename.RenameLaneUseCase;
//import ntut.csie.sslab.kanban.usecase.lane.rename.RenameLaneUseCaseImpl;
//import ntut.csie.sslab.kanban.usecase.lane.get.GetLaneUseCase;
//import ntut.csie.sslab.kanban.usecase.lane.get.GetLaneUseCaseImpl;
//import ntut.csie.sslab.kanban.usecase.lane.stage.create.CreateStageInput;
//import ntut.csie.sslab.kanban.usecase.lane.stage.create.CreateStageUseCase;
//import ntut.csie.sslab.kanban.usecase.lane.stage.create.CreateStageUseCaseImpl;
//import ntut.csie.sslab.kanban.usecase.lane.swimLane.create.CreateSwimLaneInput;
//import ntut.csie.sslab.kanban.usecase.lane.swimLane.create.CreateSwimLaneUseCase;
//import ntut.csie.sslab.kanban.usecase.lane.swimLane.create.CreateSwimLaneUseCaseImpl;
//import ntut.csie.sslab.kanban.usecase.workflow.WorkflowRepository;
//import ntut.csie.sslab.kanban.usecase.workflow.create.CreateWorkflowInput;
//import ntut.csie.sslab.kanban.usecase.workflow.create.CreateWorkflowUseCase;
//import ntut.csie.sslab.kanban.usecase.workflow.create.CreateWorkflowUseCaseImpl;
//import ntut.csie.sslab.kanban.usecase.eventhandler.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayNameGeneration;
//import org.junit.jupiter.api.DisplayNameGenerator;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.adapter.gateway.eventbus.google.NotifyBoardAdapter;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardRepositoryImpl;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardRepositoryPeer;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.FigureRepositoryImpl;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.FigureRepositoryPeer;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.line.LineRepositoryImpl;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardInput;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardUseCaseImpl;
import ntut.csie.sslab.miro.usecase.board.enter.EnterBoardInput;
import ntut.csie.sslab.miro.usecase.board.enter.EnterBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.enter.EnterBoardUseCaseImpl;
import ntut.csie.sslab.miro.usecase.board.leave.LeaveBoardInput;
import ntut.csie.sslab.miro.usecase.board.leave.LeaveBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.leave.LeaveBoardUseCaseImpl;
import ntut.csie.sslab.miro.usecase.eventhandler.NotifyBoard;
import ntut.csie.sslab.miro.usecase.figure.FigureRepository;
import ntut.csie.sslab.miro.usecase.figure.sticker.create.CreateStickerInput;
import ntut.csie.sslab.miro.usecase.figure.sticker.create.CreateStickerUseCase;
import ntut.csie.sslab.miro.usecase.figure.sticker.create.CreateStickerUseCaseImpl;
import ntut.csie.sslab.miro.usecase.figure.sticker.delete.DeleteStickerInput;
import ntut.csie.sslab.miro.usecase.figure.sticker.delete.DeleteStickerUseCase;
import ntut.csie.sslab.miro.usecase.figure.sticker.delete.DeleteStickerUseCaseImpl;
import ntut.csie.sslab.miro.usecase.line.LineRepository;
import ntut.csie.sslab.miro.usecase.line.create.CreateLineInput;
import ntut.csie.sslab.miro.usecase.line.create.CreateLineUseCase;
import ntut.csie.sslab.miro.usecase.line.create.CreateLineUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= JpaApplicationTest.class)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public abstract class AbstractSpringBootJpaTest {

    protected BoardRepository boardRepository;
    protected FigureRepository figureRepository;
    protected DomainEventBus domainEventBus;
    protected EventListener eventListener;
    protected BoardSessionBroadcaster boardSessionBroadcaster;
    protected BoardRepository boardMdbRepository;
    protected LineRepository lineRepository;

    @Autowired
    protected BoardRepositoryPeer boardRepositoryPeer;

//    @Autowired
//    protected BoardRepositoryMongoDbPeer boardRepositoryMongoDbPeer;

    @Autowired
    protected FigureRepositoryPeer figureRepositoryPeer;

    public NotifyBoardAdapter notifyBoardAdapter;

    @BeforeEach
    public void setUp() {
        boardRepository = new BoardRepositoryImpl(boardRepositoryPeer);
        figureRepository = new FigureRepositoryImpl(figureRepositoryPeer);
        lineRepository = new LineRepositoryImpl();
        domainEventBus = new GoogleEventBus();
        eventListener = new EventListener();
        notifyBoardAdapter = new NotifyBoardAdapter(new NotifyBoard(boardRepository, domainEventBus));
        domainEventBus.register(notifyBoardAdapter);
        domainEventBus.register(eventListener);
//        boardMdbRepository = new BoardRepositoryMdbImpl(boardRepositoryMongoDbPeer);
    }

//    protected String createWorkspace() {
//        String boardId = UUID.randomUUID().toString();
//        CreateWorkspaceUseCase createWorkspaceUseCase = new CreateWorkspaceUseCaseImpl(workspaceRepository, domainEventBus);
//        CreateWorkspaceInput input = createWorkspaceUseCase.newInput();
//        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
//        input.setBoardId(boardId);
//        createWorkspaceUseCase.execute(input, output);
//        return output.getId();
//    }

    protected class EventListener {
        private int eventCount;
        private List<DomainEvent> domainEvents;

        public EventListener() {
            this.domainEvents = new ArrayList<>();
        }

        @Subscribe
        public void eventHandler(DomainEvent domainEvent){
            domainEvents.add(domainEvent);
        }

        public int getEventCount() {
            return domainEvents.size();
        }

        public DomainEvent getEvent(int n){
            return domainEvents.get(n);
        }

        public void clearEventCount() {
            domainEvents.clear();
        }
    }
    protected String createSticker(String boardId, String content, int width, int length, String color, Coordinate position) {
        CreateStickerUseCase createStickerUseCase = new CreateStickerUseCaseImpl(figureRepository, domainEventBus);
        CreateStickerInput input = createStickerUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setContent(content);
        input.setWidth(width);
        input.setLength(length);
        input.setColor(color);
        input.setPosition(position);

        createStickerUseCase.execute(input, output);

        return output.getId();
    }

    protected String createBoard(String boardId, String boardName) {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCaseImpl(boardRepository, domainEventBus);
        CreateBoardInput input = createBoardUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setBoardName(boardName);

        createBoardUseCase.execute(input, output);

        return output.getId();
    }

    protected String deleteSticker(String stickerId) {
        DeleteStickerUseCase deleteStickerUseCase = new DeleteStickerUseCaseImpl(figureRepository, domainEventBus);
        DeleteStickerInput input = deleteStickerUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setFigureId(stickerId);

        deleteStickerUseCase.execute(input, output);

        return output.getId();
    }

    protected String enterBoard(String boardId, String userId) {
        EnterBoardUseCase enterBoardUseCase = new EnterBoardUseCaseImpl(boardRepository, domainEventBus);
        EnterBoardInput input = enterBoardUseCase.newInput();
        CqrsCommandOutput output = CqrsCommandPresenter.newInstance();
        input.setUserId(userId);
        input.setBoardId(boardId);

        enterBoardUseCase.execute(input, output);

        return output.getId();
    }

    protected void leaveBoard(String boardId, String boardSessionId) {
        LeaveBoardUseCase leaveBoardUseCase = new LeaveBoardUseCaseImpl(boardRepository, domainEventBus);
        LeaveBoardInput input = leaveBoardUseCase.newInput();
        CqrsCommandOutput output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setBoardSessionId(boardSessionId);

        leaveBoardUseCase.execute(input, output);
    }

    protected String createLine(String boardId, String sourceFigureId, String targetFigureId, Coordinate sourcePosition, Coordinate targetPosition) {
        CreateLineUseCase createLineUseCase = new CreateLineUseCaseImpl(lineRepository, domainEventBus);
        CreateLineInput input = createLineUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setSourceId(sourceFigureId);
        input.setTargetId(targetFigureId);
        input.setSourcePosition(sourcePosition);
        input.setTargetPosition(targetPosition);

        createLineUseCase.execute(input, output);

        return output.getId();
    }

//
//
//    public CreateBoardUseCase newCreateBoardUseCase (){
//        return new CreateBoardUseCaseImpl(boardRepository, domainEventBus);
//    }
//
//    public GetBoardContentUseCase newGetBoardContentUseCase() {
//        return new GetBoardContentUseCaseImpl(boardRepository, workflowRepository, cardRepository, domainEventBus);
//    }
//
//    public CreateWorkflowUseCase newCreateWorkflowUseCase() {
//        return new CreateWorkflowUseCaseImpl(workflowRepository, domainEventBus);
//    }
//
//    public CreateStageUseCase newCreateStageUseCase() {
//        return new CreateStageUseCaseImpl(workflowRepository, domainEventBus);
//    }
//
//    public RenameLaneUseCase newRenameLaneUseCase() {
//        return new RenameLaneUseCaseImpl(workflowRepository, domainEventBus);
//    }
//
//    public CreateSwimLaneUseCase newCreateSwimLaneUseCase() {
//        return new CreateSwimLaneUseCaseImpl(workflowRepository, domainEventBus);
//    }
//
//    public GetLaneUseCase newGetLaneByIdUseCase() {
//        return new GetLaneUseCaseImpl(workflowRepository);
//    }
//
//    public CreateCardUseCase newCreateCardUseCase() {
//        return new CreateCardUseCaseImpl(cardRepository, domainEventBus);
//    }
//
//    public ChangeCardDeadlineUseCase newChangeCardDeadlineUseCase() {
//        return new ChangeCardDeadlineUseCaseImpl(cardRepository, domainEventBus);
//    }
//
//    public DeleteCardUseCase newDeleteCardUseCase() {
//        return new DeleteCardUseCaseImpl(cardRepository, domainEventBus);
//    }
//
//    public ChangeCardNoteUseCase newChangeCardNotesUseCase() {
//        return new ChangeCardNoteUseCaseImpl(cardRepository, domainEventBus);
//    }
//
//
//    public ChangeCardTypeUseCase newChangeCardTypeUseCase() {
//        return new ChangeCardTypeUseCaseImpl(cardRepository, domainEventBus);
//    }
//
//    public ChangeCardEstimateUseCase newChangeCardEstimateUseCase() {
//        return new ChangeCardEstimateUseCaseImpl(cardRepository, domainEventBus);
//    }
//
//    public ChangeCardDescriptionUseCase newChangeCardDescriptionUseCase() {
//        return new ChangeCardDescriptionUseCaseImpl(cardRepository, domainEventBus);
//    }
//
//    public GetCardUseCase newGetCardByIdUseCase() {
//        return new GetCardUseCaseImpl(cardRepository);
//    }
//
//
//    public String createBoard(String teamId, String name, String userId){
//        CreateBoardUseCase createBoardUseCase = newCreateBoardUseCase();
//
//        CreateBoardInput input = createBoardUseCase.newInput();
//        input.setTeamId(teamId);
//        input.setName(name);
//        input.setUserId(userId);
//
//        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
//        createBoardUseCase.execute(input, output);
//
//        return output.getId();
//    }
//
//    public String createWorkflow(String boardId, String workflowName, String userId, String username) {
//        CreateWorkflowUseCase createWorkflowUseCase = newCreateWorkflowUseCase();
//
//        CreateWorkflowInput input = createWorkflowUseCase.newInput();
//        input.setWorkflowName(workflowName);
//        input.setBoardId(boardId);
//        input.setUserId(userId);
//        input.setUsername(username);
//
//        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
//
//        createWorkflowUseCase.execute(input, output);
//
//        return output.getId();
//    }
//
//    public String createStage(
//            String boardId,
//            String userId,
//            String username,
//            String workflowId,
//            String parentId,
//            String name,
//            int wipLimit,
//            String laneType) {
//
//        CreateStageUseCase createStageUseCase = newCreateStageUseCase();
//
//        CreateStageInput input = createStageUseCase.newInput();
//        input.setBoardId(boardId);
//        input.setWorkflowId(workflowId);
//        input.setParentId(parentId);
//        input.setStageName(name);
//        input.setWipLimit(wipLimit);
//        input.setLaneType(laneType);
//        input.setUserId(userId);
//        input.setUsername(username);
//
//        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
//
//        createStageUseCase.execute(input, output);
//
//        return output.getId();
//    }
//
//    public String createSwimLane(
//            String workflowId,
//            String parentId,
//            String name,
//            int wipLimit,
//            String laneType,
//            String userId,
//            String username,
//            String boardId) {
//
//        CreateSwimLaneUseCase createSwimLaneUseCase = newCreateSwimLaneUseCase();
//
//        CreateSwimLaneInput input = createSwimLaneUseCase.newInput();
//        input.setWorkflowId(workflowId);
//        input.setParentId(parentId);
//        input.setName(name);
//        input.setWipLimit(wipLimit);
//        input.setLaneType(laneType);
//        input.setUserId(userId);
//        input.setUsername(username);
//        input.setBoardId(boardId);
//
//        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
//
//        createSwimLaneUseCase.execute(input, output);
//
//        return output.getId();
//    }
//
//    public String renameLane(String workflowId, String laneId, String newName, String userId, String username){
//
//        RenameLaneUseCase renameLaneUseCase = newRenameLaneUseCase();
//        RenameLaneInput input = renameLaneUseCase.newInput();
//        input.setWorkflowId(workflowId);
//        input.setLaneId(laneId);
//        input.setNewName(newName);
//        input.setUserId(userId);
//        input.setUsername(username);
//        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
//        renameLaneUseCase.execute(input, output);
//
//        return  output.getId();
//    }
//
//    public String createCard(String userId,
//                             String workflowId,
//                             String laneId,
//                             String description,
//                             String estimate,
//                             String notes,
//                             String deadline,
//                             String type,
//                             String username,
//                             String boardId){
//
//        CreateCardUseCase createCardUseCase = newCreateCardUseCase();
//        CreateCardInput createCardInput = createCardUseCase.newInput();
//        createCardInput.setWorkflowId(workflowId);
//        createCardInput.setLaneId(laneId);
//        createCardInput.setUserId(userId);
//        createCardInput.setDescription(description);
//        createCardInput.setEstimate(estimate);
//        createCardInput.setNote(notes);
//        createCardInput.setDeadline(deadline);
//        createCardInput.setType(type);
//        createCardInput.setUsername(username);
//        createCardInput.setBoardId(boardId);
//
//        CqrsCommandPresenter addCardOutput = CqrsCommandPresenter.newInstance();
//
//        createCardUseCase.execute(createCardInput, addCardOutput);
//
//        Card card = cardRepository.findById(addCardOutput.getId()).get();
//
//
//        assertNotNull(card);
//        assertEquals(userId, card.getUserId());
//        assertEquals(laneId, card.getLaneId());
//        assertEquals(description, card.getDescription());
//        assertEquals(estimate, card.getEstimate());
//        assertEquals(notes, card.getNotes());
//        assertEquals(deadline, card.getDeadline());
//        assertEquals(type, card.getType().toString());
//
//        return addCardOutput.getId();
//    }
//
//    public void deleteCard(String workflowId, String laneId, String cardId, String userId, String username, String boardId) {
//        DeleteCardUseCase deleteCardUseCase = newDeleteCardUseCase();
//
//        DeleteCardInput input = deleteCardUseCase.newInput();
//        input.setWorkflowId(workflowId);
//        input.setLaneId(laneId);
//        input.setCardId(cardId);
//        input.setUserId(userId);
//        input.setUsername(username);
//        input.setBoardId(boardId);
//
//        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
//        deleteCardUseCase.execute(input, output);
//    }
//
//    public void changeCardDescription(String cardId, String newDescription, String boardId, String userId, String username){
//        ChangeCardDescriptionUseCase changeCardDescriptionUseCase = newChangeCardDescriptionUseCase();
//
//        ChangeCardDescriptionInput input = changeCardDescriptionUseCase.newInput();
//        input.setCardId(cardId);
//        input.setNewDescription(newDescription);
//        input.setBoardId(boardId);
//        input.setUserId(userId);
//        input.setUsername(username);
//
//        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
//        changeCardDescriptionUseCase.execute(input, output);
//    }
//
//    public void changeCardEstimate(String cardId, String newEstimate, String boardId, String userId, String username){
//        ChangeCardEstimateUseCase changeCardEstimateUseCase = newChangeCardEstimateUseCase();
//
//        ChangeCardEstimateInput input = changeCardEstimateUseCase.newInput();
//        input.setCardId(cardId);
//        input.setNewEstimate(newEstimate);
//        input.setBoardId(boardId);
//        input.setUserId(userId);
//        input.setUsername(username);
//
//
//        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
//        changeCardEstimateUseCase.execute(input, output);
//    }
//
//
//    public void changeCardDeadline(String cardId, String newDeadline, String boardId, String userId, String username) {
//        ChangeCardDeadlineUseCase changeCardDeadlineUseCase = newChangeCardDeadlineUseCase();
//
//        ChangeCardDeadlineInput input = changeCardDeadlineUseCase.newInput();
//        input.setCardId(cardId);
//        input.setNewDeadline(newDeadline);
//        input.setUserId(userId);
//        input.setUsername(username);
//        input.setBoardId(boardId);
//
//        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
//        changeCardDeadlineUseCase.execute(input, output);
//    }
//
//    public void changeCardNotes(String cardId, String newNotes, String boardId, String userId, String username) {
//        ChangeCardNoteUseCase changeCardNoteUseCase = newChangeCardNotesUseCase();
//
//        ChangeCardNoteInput input = changeCardNoteUseCase.newInput();
//        input.setCardId(cardId);
//        input.setNewNotes(newNotes);
//        input.setUserId(userId);
//        input.setUsername(username);
//        input.setBoardId(boardId);
//
//        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
//        changeCardNoteUseCase.execute(input, output);
//    }
//
//    public void changeCardType(String cardId, String newType, String boardId, String userId, String username) {
//
//        ChangeCardTypeUseCase changeCardTypeUseCase = newChangeCardTypeUseCase();
//
//        ChangeCardTypeInput input = changeCardTypeUseCase.newInput();
//        input.setCardId(cardId);
//        input.setNewType(newType);
//        input.setUserId(userId);
//        input.setUsername(username);
//        input.setBoardId(boardId);
//
//        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
//        changeCardTypeUseCase.execute(input, output);
//    }
//
//    public BoardRepository getBoardRepository(){
//        return boardRepository;
//    }
}
