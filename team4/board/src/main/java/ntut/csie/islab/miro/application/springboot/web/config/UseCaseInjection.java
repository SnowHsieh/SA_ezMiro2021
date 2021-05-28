package ntut.csie.islab.miro.application.springboot.web.config;

import ntut.csie.islab.miro.usecase.board.BoardRepository;
import ntut.csie.islab.miro.usecase.board.changefigureorder.ChangeFigureOrderListOnBoardUseCase;
import ntut.csie.islab.miro.usecase.board.createboard.CreateBoardUseCase;
import ntut.csie.islab.miro.usecase.board.cursor.MoveCursorUseCase;
import ntut.csie.islab.miro.usecase.board.enterboard.EnterBoardUseCase;
import ntut.csie.islab.miro.usecase.board.getallusercursors.GetAllUserCursorsUseCase;
import ntut.csie.islab.miro.usecase.board.getboardcontent.GetBoardContentUseCase;
import ntut.csie.islab.miro.usecase.board.leaveboard.LeaveBoardUseCase;
import ntut.csie.islab.miro.usecase.eventhandler.NotifyBoard;
import ntut.csie.islab.miro.usecase.textfigure.StickyNoteRepository;
import ntut.csie.islab.miro.usecase.textfigure.stickynote.changecolor.ChangeStickyNoteColorUseCase;
import ntut.csie.islab.miro.usecase.textfigure.stickynote.changecontent.ChangeStickyNoteContentUseCase;
import ntut.csie.islab.miro.usecase.textfigure.stickynote.create.CreateStickyNoteUseCase;
import ntut.csie.islab.miro.usecase.textfigure.stickynote.delete.DeleteStickyNoteUseCase;
import ntut.csie.islab.miro.usecase.textfigure.stickynote.move.MoveStickyNoteUseCase;
import ntut.csie.islab.miro.usecase.textfigure.stickynote.resize.ResizeStickyNoteUseCase;
import ntut.csie.islab.miro.usecase.websocket.BoardSessionBroadcaster;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration("EzMiroUserCaseInjection")
public class UseCaseInjection {
    private BoardRepository boardRepository;
    private StickyNoteRepository stickyNoteRepository;
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
        return new GetBoardContentUseCase(eventBus,boardRepository, stickyNoteRepository);
    }
    @Bean(name = "getAllUserCursorsUseCase")
    public GetAllUserCursorsUseCase getAllUserCursorsUseCase() {
        return new GetAllUserCursorsUseCase(eventBus,boardRepository);
    }


    @Bean(name = "createStickyNoteUseCase")
    public CreateStickyNoteUseCase createStickyNoteUseCase() {
        return new CreateStickyNoteUseCase(stickyNoteRepository,eventBus);
    }



    @Bean(name = "deleteStickyNoteUseCase")
    public DeleteStickyNoteUseCase deleteStickyNoteUseCase() {
        return new DeleteStickyNoteUseCase(stickyNoteRepository,eventBus);
    }
    @Bean(name = "moveStickyNoteUseCase")
    public MoveStickyNoteUseCase moveStickyNoteUseCase() {
        return new MoveStickyNoteUseCase(stickyNoteRepository,eventBus);
    }

    @Bean(name = "changeStickyNoteContentUseCase")
    public ChangeStickyNoteContentUseCase changeStickyNoteContentUseCase() {
        return new ChangeStickyNoteContentUseCase(stickyNoteRepository,eventBus);
    }

    @Bean(name = "changeStickyNoteColorUseCase")
    public ChangeStickyNoteColorUseCase changeStickyNoteColorUseCase() {
        return new ChangeStickyNoteColorUseCase(stickyNoteRepository,eventBus);
    }

    @Bean(name = "resizeStickyNoteUseCase")
    public ResizeStickyNoteUseCase resizeStickyNoteUseCase() {
        return new ResizeStickyNoteUseCase(stickyNoteRepository,eventBus);
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
    public void setFigureRepository(StickyNoteRepository stickyNoteRepository) {
        this.stickyNoteRepository = stickyNoteRepository;
    }

    @Autowired
    public void setEventBus(DomainEventBus eventBus) {
        this.eventBus = eventBus;
    }


}
