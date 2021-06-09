package ntut.csie.selab.application.springboot.web.config;

import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.board.create.CreateBoardUseCase;
import ntut.csie.selab.usecase.board.edit.zorder.ChangeZOrderOfWidgetUseCase;
import ntut.csie.selab.usecase.board.enterboard.EnterBoardUseCase;
import ntut.csie.selab.usecase.board.leaveboard.LeaveBoardUseCase;
import ntut.csie.selab.usecase.board.movecursor.MoveCursorUseCase;
import ntut.csie.selab.usecase.board.query.getcontent.GetBoardContentUseCase;
import ntut.csie.selab.usecase.eventHandler.NotifyBoard;
import ntut.csie.selab.usecase.eventHandler.NotifyLine;
import ntut.csie.selab.usecase.eventHandler.NotifyUsersInBoard;
import ntut.csie.selab.usecase.websocket.WebSocket;
import ntut.csie.selab.usecase.widget.LineRepository;
import ntut.csie.selab.usecase.widget.StickyNoteRepository;
import ntut.csie.selab.usecase.widget.line.create.CreateLineUseCase;
import ntut.csie.selab.usecase.widget.line.delete.DeleteLineUseCase;
import ntut.csie.selab.usecase.widget.line.link.LinkLineUseCase;
import ntut.csie.selab.usecase.widget.line.move.MoveLineUseCase;
import ntut.csie.selab.usecase.widget.query.getwidget.GetWidgetUseCase;
import ntut.csie.selab.usecase.widget.stickynote.create.CreateStickyNoteUseCase;
import ntut.csie.selab.usecase.widget.stickynote.delete.DeleteStickyNoteUseCase;
import ntut.csie.selab.usecase.widget.stickynote.edit.color.ChangeColorOfStickyNoteUseCase;
import ntut.csie.selab.usecase.widget.stickynote.edit.fontsize.EditFontSizeOfStickyNoteUseCase;
import ntut.csie.selab.usecase.widget.stickynote.edit.text.EditTextOfStickyNoteUseCase;
import ntut.csie.selab.usecase.widget.stickynote.move.MoveStickyNoteUseCase;
import ntut.csie.selab.usecase.widget.stickynote.resize.ResizeStickyNoteUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("MiroUseCaseInjection")
public class UseCaseInjection {
    private BoardRepository boardRepository;
    private StickyNoteRepository stickyNoteRepository;
    private LineRepository lineRepository;
    private DomainEventBus eventBus;
    private WebSocket boardWebSocket;

    @Bean(name="createNotifyBoard")
    public NotifyBoard createNotifyBoard() {
        return new NotifyBoard(boardRepository, eventBus);
    }

    @Bean(name="createNotifyUsersInBoard")
    public NotifyUsersInBoard createNotifyUsersInBoard() {
        return new NotifyUsersInBoard(boardRepository, stickyNoteRepository, lineRepository, eventBus, boardWebSocket);
    }

    @Bean(name="createNotifyLine")
    public NotifyLine createNotifyLine() {
        return new NotifyLine(boardRepository, lineRepository, eventBus);
    }

    @Bean(name="GetBoardContentUseCase")
    public GetBoardContentUseCase getBoardContentUseCase() {
        return new GetBoardContentUseCase(boardRepository, stickyNoteRepository, lineRepository);
    }

    @Bean(name="CreateBoardUseCase")
    public CreateBoardUseCase createBoardUseCase() {
        return new CreateBoardUseCase(boardRepository, eventBus);
    }

    @Bean(name="CreateStickyNoteUseCase")
    public CreateStickyNoteUseCase createStickyNoteUseCase() { return new CreateStickyNoteUseCase(stickyNoteRepository, eventBus); }

    @Bean(name="CreateLineUseCase")
    public CreateLineUseCase createLineUseCase() { return new CreateLineUseCase(lineRepository, eventBus); }

    @Bean(name="MoveStickyNoteUseCase")
    public MoveStickyNoteUseCase moveStickyNoteUseCase() {
        return new MoveStickyNoteUseCase(stickyNoteRepository, eventBus);
    }

    @Bean(name="MoveCursorUseCase")
    public MoveCursorUseCase moveCursorUseCase() {
        return new MoveCursorUseCase(boardRepository, eventBus);
    }

    @Bean(name="ChangeColorOfStickyNoteUseCase")
    public ChangeColorOfStickyNoteUseCase changeColorOfStickyNoteUseCase() { return new ChangeColorOfStickyNoteUseCase(stickyNoteRepository, eventBus); }

    @Bean(name="ResizeStickyNoteUseCase")
    public ResizeStickyNoteUseCase resizeStickyNoteUseCase() { return new ResizeStickyNoteUseCase(stickyNoteRepository, eventBus); }

    @Bean(name="GetWidgetUseCase")
    public GetWidgetUseCase getWidgetUseCase() {
        return new GetWidgetUseCase(stickyNoteRepository);
    }

    @Bean(name="EditTextOfStickyNoteUseCase")
    public EditTextOfStickyNoteUseCase editTextOfStickyNoteUseCase() {
        return new EditTextOfStickyNoteUseCase(stickyNoteRepository, eventBus);
    }

    @Bean(name="DeleteStickyNoteUseCase")
    public DeleteStickyNoteUseCase deleteStickyNoteUseCase() {
        return new DeleteStickyNoteUseCase(stickyNoteRepository, eventBus);
    }

    @Bean(name="ChangeZOrderOfWidgetUseCase")
    public ChangeZOrderOfWidgetUseCase changeZOrderOfWidgetUseCase() {
        return new ChangeZOrderOfWidgetUseCase(boardRepository, eventBus);
    }

    @Bean("EditFontSizeOfStickyNoteUseCase")
    public EditFontSizeOfStickyNoteUseCase editFontSizeOfStickyNoteUseCase() {
        return new EditFontSizeOfStickyNoteUseCase(stickyNoteRepository, eventBus);
    }

    @Bean("DeleteLineUseCase")
    public DeleteLineUseCase deleteLineUseCase() {
        return new DeleteLineUseCase(lineRepository, eventBus);
    }

    @Bean("EnterBoardUseCase")
    public EnterBoardUseCase enterBoardUseCase() {
        return new EnterBoardUseCase(boardRepository, eventBus);
    }

    @Bean("LeaveBoardUseCase")
    public LeaveBoardUseCase leaveBoardUseCase() {
        return new LeaveBoardUseCase(boardRepository, eventBus);
    }

    @Bean("MoveLineUseCase")
    public MoveLineUseCase moveLineUseCase() {
        return new MoveLineUseCase(lineRepository, eventBus);
    }

    @Bean("LinkLineUseCase")
    public LinkLineUseCase linkLineUseCase() {
        return new LinkLineUseCase(lineRepository, stickyNoteRepository, eventBus);
    }

    @Autowired
    public void setBoardRepository(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Autowired
    public void setWidgetRepository(StickyNoteRepository stickyNoteRepository) { this.stickyNoteRepository = stickyNoteRepository; }

    @Autowired
    public void setLineRepository(LineRepository lineRepository) { this.lineRepository = lineRepository; }

    @Autowired
    public void setEventBus(DomainEventBus eventBus) { this.eventBus = eventBus; }

    @Autowired
    public void setBoardWebSocket(WebSocket boardWebSocket) {
        this.boardWebSocket = boardWebSocket;
    }
}
