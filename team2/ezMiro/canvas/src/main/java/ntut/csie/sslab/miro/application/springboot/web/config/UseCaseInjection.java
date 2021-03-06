package ntut.csie.sslab.miro.application.springboot.web.config;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.adapter.controller.websocket.WebSocketBroadcaster;
import ntut.csie.sslab.miro.adapter.gateway.eventbus.NotifyBoardAdapter;
import ntut.csie.sslab.miro.adapter.gateway.eventbus.NotifyCursorAdapter;
import ntut.csie.sslab.miro.adapter.gateway.eventbus.NotifyEventBroadcasterAdapter;
import ntut.csie.sslab.miro.adapter.gateway.eventbus.NotifyFigureAdapter;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardUseCaseImpl;
import ntut.csie.sslab.miro.usecase.board.enter.EnterBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.enter.EnterBoardUseCaseImpl;
import ntut.csie.sslab.miro.usecase.board.leave.LeaveBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.leave.LeaveBoardUseCaseImpl;
import ntut.csie.sslab.miro.usecase.cursor.CursorRepository;
import ntut.csie.sslab.miro.usecase.cursor.move.MoveCursorUseCase;
import ntut.csie.sslab.miro.usecase.cursor.move.MoveCursorUseCaseImpl;
import ntut.csie.sslab.miro.usecase.eventhandler.NotifyBoard;
import ntut.csie.sslab.miro.usecase.eventhandler.NotifyCursor;
import ntut.csie.sslab.miro.usecase.eventhandler.NotifyFigure;
import ntut.csie.sslab.miro.usecase.line.connecttofigure.ConnectLineToFigureUseCase;
import ntut.csie.sslab.miro.usecase.line.connecttofigure.ConnectLineToFigureUseCaseImpl;
import ntut.csie.sslab.miro.usecase.line.create.CreateLineUseCase;
import ntut.csie.sslab.miro.usecase.line.create.CreateLineUseCaseImpl;
import ntut.csie.sslab.miro.usecase.line.delete.DeleteLineUseCase;
import ntut.csie.sslab.miro.usecase.line.delete.DeleteLineUseCaseImpl;
import ntut.csie.sslab.miro.usecase.line.disconnectfromfigure.DisconnectLineFromFigureUseCase;
import ntut.csie.sslab.miro.usecase.line.disconnectfromfigure.DisconnectLineFromFigureUseCaseImpl;
import ntut.csie.sslab.miro.usecase.line.move.MoveLinePointUseCase;
import ntut.csie.sslab.miro.usecase.line.move.MoveLinePointUseCaseImpl;
import ntut.csie.sslab.miro.usecase.line.move.MoveLineUseCase;
import ntut.csie.sslab.miro.usecase.line.move.MoveLineUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.delete.DeleteNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.delete.DeleteNoteUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.edit.color.ChangeNoteColorUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.color.ChangeNoteColorUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.edit.description.ChangeNoteDescriptionUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.description.ChangeNoteDescriptionUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.edit.size.ChangeNoteSizeUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.size.ChangeNoteSizeUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.edit.zorder.back.SendNoteToBackUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.zorder.back.SendNoteToBackUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.edit.zorder.front.BringNoteToFrontUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.zorder.front.BringNoteToFrontUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.get.GetNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.get.GetNoteUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.move.MoveNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.move.MoveNoteUseCaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.ExecutorService;

@Configuration("MiroUseCaseInjection")
public class UseCaseInjection {
    private BoardRepository boardRepository;
    private FigureRepository figureRepository;
    private CursorRepository cursorRepository;
    private DomainEventBus eventBus;
    private ExecutorService executor;

    @Autowired
    private WebSocketBroadcaster webSocketBroadcaster;

    @Bean(name="createBoardUseCase")
    public CreateBoardUseCase createBoardUseCase() {
        return new CreateBoardUseCaseImpl(boardRepository, eventBus);
    }

    @Bean(name="enterBoardUseCase")
    public EnterBoardUseCase enterBoardUseCase() {
        return new EnterBoardUseCaseImpl(boardRepository, eventBus);
    }

    @Bean(name="leaveBoardUseCase")
    public LeaveBoardUseCase leaveBoardUseCase() {
        return new LeaveBoardUseCaseImpl(boardRepository, eventBus);
    }

    @Bean(name="createNoteUseCase")
    public CreateNoteUseCase createNoteUseCase() {
        return new CreateNoteUseCaseImpl(figureRepository, eventBus);
    }

    @Bean(name="getNoteUseCase")
    public GetNoteUseCase getNoteUseCase() {
        return new GetNoteUseCaseImpl(figureRepository);
    }

    @Bean(name="moveNoteUseCase")
    public MoveNoteUseCase moveNoteUseCase() {
        return new MoveNoteUseCaseImpl(figureRepository, eventBus);
    }

    @Bean(name="changeNoteColorUseCase")
    public ChangeNoteColorUseCase changeNoteColorUseCase() {
        return new ChangeNoteColorUseCaseImpl(figureRepository, eventBus);
    }

    @Bean(name="changeNoteDescriptionUseCase")
    public ChangeNoteDescriptionUseCase changeNoteDescriptionUseCase() {
        return new ChangeNoteDescriptionUseCaseImpl(figureRepository, eventBus);
    }

    @Bean(name="changeNoteSizeUseCase")
    public ChangeNoteSizeUseCase changeNoteSizeUseCase() {
        return new ChangeNoteSizeUseCaseImpl(figureRepository, eventBus);
    }

    @Bean(name="bringNoteToFrontUseCase")
    public BringNoteToFrontUseCase bringNoteToFrontUseCase() {
        return new BringNoteToFrontUseCaseImpl(figureRepository, eventBus);
    }

    @Bean(name="sendNoteToBackUseCase")
    public SendNoteToBackUseCase sendNoteToBackUseCase() {
        return new SendNoteToBackUseCaseImpl(figureRepository, eventBus);
    }

    @Bean(name="moveCursorUseCase")
    public MoveCursorUseCase moveCursorUseCase() {
        return new MoveCursorUseCaseImpl(cursorRepository, eventBus);
    }

    @Bean(name="deleteNoteUseCase")
    public DeleteNoteUseCase deleteNoteUseCase() {
        return new DeleteNoteUseCaseImpl(figureRepository, eventBus);
    }

    @Bean(name="createLineUseCase")
    public CreateLineUseCase createLineUseCase() {
        return new CreateLineUseCaseImpl(figureRepository, eventBus);
    }

    @Bean(name="moveLineUseCase")
    public MoveLineUseCase moveLineUseCase() {
        return new MoveLineUseCaseImpl(figureRepository, eventBus);
    }

    @Bean(name="deleteLineUseCase")
    public DeleteLineUseCase deleteLineUseCase() {
        return new DeleteLineUseCaseImpl(figureRepository, eventBus);
    }

    @Bean(name="moveLinePointUseCase")
    public MoveLinePointUseCase moveLinePointUseCase() {
        return new MoveLinePointUseCaseImpl(figureRepository, eventBus);
    }

    @Bean(name="connectLineToFigureUseCase")
    public ConnectLineToFigureUseCase connectLineToFigureUseCase() {
        return new ConnectLineToFigureUseCaseImpl(figureRepository, eventBus);
    }

    @Bean(name="disconnectLineFromFigureUseCase")
    public DisconnectLineFromFigureUseCase disconnectLineFromFigureUseCase() {
        return new DisconnectLineFromFigureUseCaseImpl(figureRepository, eventBus);
    }

    @Bean(name="notifyBoardAdapter")
    public NotifyBoardAdapter notifyBoardAdapter() {
        return new NotifyBoardAdapter(new NotifyBoard(figureRepository, boardRepository, eventBus));
    }

    @Bean(name="notifyCursorAdapter")
    public NotifyCursorAdapter notifyCursorAdapter() {
        return new NotifyCursorAdapter(new NotifyCursor(cursorRepository, eventBus));
    }

    @Bean(name="notifyFigureAdapter")
    public NotifyFigureAdapter notifyFigureAdapter() {
        return new NotifyFigureAdapter(new NotifyFigure(figureRepository, eventBus));
    }

    @Bean(name="notifyEventBroadcasterAdapter")
    public NotifyEventBroadcasterAdapter notifyEventBroadcasterAdapter() {
        return new NotifyEventBroadcasterAdapter(webSocketBroadcaster, cursorRepository, boardRepository, figureRepository);
    }

    @Autowired
    public void setBoardRepository(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Autowired
    public void setFigureRepository(FigureRepository figureRepository) {
        this.figureRepository = figureRepository;
    }

    @Autowired
    public void setCursorRepository(CursorRepository cursorRepository) {
        this.cursorRepository = cursorRepository;
    }

    @Autowired
    public void setEventBus(DomainEventBus eventBus) { this.eventBus = eventBus; }
}