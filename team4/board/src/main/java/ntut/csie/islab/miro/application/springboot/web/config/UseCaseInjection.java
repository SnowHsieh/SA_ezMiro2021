package ntut.csie.islab.miro.application.springboot.web.config;

import ntut.csie.islab.miro.adapter.repository.board.BoardRepository;
import ntut.csie.islab.miro.adapter.repository.textFigure.TextFigureRepository;
import ntut.csie.islab.miro.usecase.board.*;
import ntut.csie.islab.miro.usecase.board.cursor.MoveCursorUseCase;
import ntut.csie.islab.miro.usecase.eventHandler.NotifyBoard;
import ntut.csie.islab.miro.usecase.eventHandler.NotifyBoardSessionBroadcaster;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.*;
import ntut.csie.islab.miro.usecase.webSocket.BoardSessionBroadcaster;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration("EzMiroUserCaseInjection")
public class UseCaseInjection {
    private BoardRepository boardRepository;
    private TextFigureRepository textFigureRepository;
    private DomainEventBus eventBus;

    @Autowired
    private BoardSessionBroadcaster boardSessionBroadcaster;

    @Bean(name="createNotifyBoard")
    public NotifyBoard createNotifyBoard() {
        return new NotifyBoard(boardRepository, eventBus);
    }

    @Bean(name = "createBoardUseCase")
    public CreateBoardUseCase createBoardUseCase() {
        return new CreateBoardUseCase(eventBus, boardRepository);
    }

    @Bean(name = "changeFigureOrderListOnBoardUseCase")
    public ChangeFigureOrderListOnBoardUseCase changeFigureOrderListOnBoardUseCase(){return new ChangeFigureOrderListOnBoardUseCase(boardRepository,eventBus);}


    @Bean(name = "getBoardContentUseCase")
    public GetBoardContentUseCase getBoardContentUseCase() {
        return new GetBoardContentUseCase(eventBus,boardRepository, textFigureRepository);
    }
    @Bean(name = "getAllUserCursorsUseCase")
    public GetAllUserCursorsUseCase getAllUserCursorsUseCase() {
        return new GetAllUserCursorsUseCase(eventBus,boardRepository);
    }


    @Bean(name = "createStickyNoteUseCase")
    public CreateStickyNoteUseCase createStickyNoteUseCase() {
        return new CreateStickyNoteUseCase(textFigureRepository,eventBus);
    }

    @Bean(name = "editStickyNoteUseCase")
    public EditStickyNoteUseCase editStickyNoteUseCase() {
        return new EditStickyNoteUseCase(textFigureRepository,eventBus);
    }

    @Bean(name = "deleteStickyNoteUseCase")
    public DeleteStickyNoteUseCase deleteStickyNoteUseCase() {
        return new DeleteStickyNoteUseCase(textFigureRepository,eventBus);
    }
    @Bean(name = "moveStickyNoteUseCase")
    public MoveStickyNoteUseCase moveStickyNoteUseCase() {
        return new MoveStickyNoteUseCase(textFigureRepository,eventBus);
    }

    @Bean(name = "changeStickyNoteContentUseCase")
    public ChangeStickyNoteContentUseCase changeStickyNoteContentUseCase() {
        return new ChangeStickyNoteContentUseCase(textFigureRepository,eventBus);
    }

    @Bean(name = "changeStickyNoteColorUseCase")
    public ChangeStickyNoteColorUseCase changeStickyNoteColorUseCase() {
        return new ChangeStickyNoteColorUseCase(textFigureRepository,eventBus);
    }

    @Bean(name = "resizeStickyNoteUseCase")
    public ResizeStickyNoteUseCase resizeStickyNoteUseCase() {
        return new ResizeStickyNoteUseCase(textFigureRepository,eventBus);
    }
    @Bean(name="moveCursorUseCase")
    public MoveCursorUseCase moveCursorUseCase() {
        return new MoveCursorUseCase(boardRepository, eventBus);
    }

    @Bean(name="enterBoardUseCase")
    public EnterBoardUseCase enterBoardUseCase() {
        return new EnterBoardUseCase(eventBus , boardRepository);
    }

    @Bean(name="leaveBoardUseCase")
    public LeaveBoardUseCase leaveBoardUseCase() {
        return new LeaveBoardUseCase(boardRepository, eventBus);
    }

//    @Bean(name="createNotifyBoardSessionBroadcaster")
//    public NotifyBoardSessionBroadcaster createNotifyBoardSessionBroadcaster() {
//        return new NotifyBoardSessionBroadcaster(boardSessionBroadcaster, boardRepository, textFigureRepository);
//    }

    @Autowired
    public void setBoardRepository(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Autowired
    public void setFigureRepository(TextFigureRepository textFigureRepository) {
        this.textFigureRepository = textFigureRepository;
    }

    @Autowired
    public void setEventBus(DomainEventBus eventBus) {
        this.eventBus = eventBus;
    }


}
