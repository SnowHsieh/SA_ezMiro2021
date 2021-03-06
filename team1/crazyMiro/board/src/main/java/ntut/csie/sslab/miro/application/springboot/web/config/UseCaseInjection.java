package ntut.csie.sslab.miro.application.springboot.web.config;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.usecase.BoardSessionBroadcaster;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.board.bringFigureToFront.BringFigureToFrontUseCase;
import ntut.csie.sslab.miro.usecase.board.bringFigureToFront.BringFigureToFrontUseCaseImpl;
import ntut.csie.sslab.miro.usecase.board.enter.EnterBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.enter.EnterBoardUseCaseImpl;
import ntut.csie.sslab.miro.usecase.board.leave.LeaveBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.leave.LeaveBoardUseCaseImpl;
import ntut.csie.sslab.miro.usecase.board.sendFigureToBack.SendFigureToBackUseCase;
import ntut.csie.sslab.miro.usecase.board.sendFigureToBack.SendFigureToBackUseCaseImpl;
import ntut.csie.sslab.miro.usecase.board.moveCursor.MoveCursorUseCaseImpl;
import ntut.csie.sslab.miro.usecase.board.moveCursor.MoveCursorUseCase;
import ntut.csie.sslab.miro.usecase.board.showCursor.ShowCursorUseCase;
import ntut.csie.sslab.miro.usecase.board.showCursor.ShowCursorUseCaseImpl;
import ntut.csie.sslab.miro.usecase.eventhandler.NotifyBoard;
import ntut.csie.sslab.miro.usecase.eventhandler.NotifyBoardSessionBroadcaster;
import ntut.csie.sslab.miro.usecase.eventhandler.NotifyLine;
import ntut.csie.sslab.miro.usecase.figure.StickerRepository;
import ntut.csie.sslab.miro.usecase.figure.line.changeSourcePosition.ChangeSourcePositionUseCase;
import ntut.csie.sslab.miro.usecase.figure.line.changeSourcePosition.ChangeSourcePositionUseCaseImpl;
import ntut.csie.sslab.miro.usecase.figure.line.changeTargetPosition.ChangeTargetPositionUseCase;
import ntut.csie.sslab.miro.usecase.figure.line.changeTargetPosition.ChangeTargetPositionUseCaseImpl;
import ntut.csie.sslab.miro.usecase.figure.sticker.changecolor.ChangeStickerColorUseCase;
import ntut.csie.sslab.miro.usecase.figure.sticker.changecolor.ChangeStickerColorUseCaseImpl;
import ntut.csie.sslab.miro.usecase.figure.sticker.changecontent.ChangeStickerContentUseCase;
import ntut.csie.sslab.miro.usecase.figure.sticker.changecontent.ChangeStickerContentUseCaseImpl;
import ntut.csie.sslab.miro.usecase.figure.sticker.changesize.ChangeStickerSizeUseCase;
import ntut.csie.sslab.miro.usecase.figure.sticker.changesize.ChangeStickerSizeUseCaseImpl;
import ntut.csie.sslab.miro.usecase.figure.sticker.create.CreateStickerUseCase;
import ntut.csie.sslab.miro.usecase.figure.sticker.create.CreateStickerUseCaseImpl;
import ntut.csie.sslab.miro.usecase.figure.sticker.delete.DeleteStickerUseCase;
import ntut.csie.sslab.miro.usecase.figure.sticker.delete.DeleteStickerUseCaseImpl;
import ntut.csie.sslab.miro.usecase.figure.sticker.move.MoveStickerUseCase;
import ntut.csie.sslab.miro.usecase.figure.sticker.move.MoveStickerUseCaseImpl;
import ntut.csie.sslab.miro.usecase.figure.line.delete.DeleteLineUseCaseImpl;
import ntut.csie.sslab.miro.usecase.figure.line.create.CreateLineUseCaseImpl;
import ntut.csie.sslab.miro.usecase.figure.line.LineRepository;
import ntut.csie.sslab.miro.usecase.figure.line.create.CreateLineUseCase;
import ntut.csie.sslab.miro.usecase.figure.line.delete.DeleteLineUseCase;
import ntut.csie.sslab.miro.usecase.figure.line.move.MoveLineUseCase;
import ntut.csie.sslab.miro.usecase.figure.line.move.MoveLineUseCaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration("MiroUseCaseInjection")
public class UseCaseInjection {


    @Autowired
    private StickerRepository stickerRepository;

    @Autowired
    private BoardRepository boardRepository;


    @Autowired
    private BoardSessionBroadcaster boardSessionBroadcaster;

    @Autowired
    private LineRepository lineRepository;

    private DomainEventBus eventBus;


    @Bean(name="createNotifyBoard")
    public NotifyBoard createNotifyBoard() {
        return new NotifyBoard(boardRepository, eventBus);
    }

    @Bean(name="createNotifyLine")
    public NotifyLine createNotifyLine() {return new NotifyLine(lineRepository, eventBus);}

    @Bean(name="createNotifyBoardSessionBroadcaster")
    public NotifyBoardSessionBroadcaster createNotifyBoardSessionBroadcaster() {
        return new NotifyBoardSessionBroadcaster(boardSessionBroadcaster, boardRepository, stickerRepository);
    }

    @Bean(name="createStickerUseCase")
    public CreateStickerUseCase createStickerUseCase() {
        return new CreateStickerUseCaseImpl(stickerRepository, eventBus);
    }

    @Bean(name="changeStickerContentUseCase")
    public ChangeStickerContentUseCase changeStickerContentUseCase() {
        return new ChangeStickerContentUseCaseImpl(stickerRepository, eventBus);
    }

    @Bean(name="moveStickerUseCase")
    public MoveStickerUseCase moveStickerUseCase() {
        return new MoveStickerUseCaseImpl(stickerRepository, eventBus);
    }

    @Bean(name="changeStickerSizeUseCase")
    public ChangeStickerSizeUseCase changeStickerSizeUseCase() {
        return new ChangeStickerSizeUseCaseImpl(stickerRepository, eventBus);
    }

    @Bean(name="changeStickerColorUseCase")
    public ChangeStickerColorUseCase changeStickerColorUseCase() {
        return new ChangeStickerColorUseCaseImpl(stickerRepository, eventBus);
    }

    @Bean(name="deleteStickerUseCase")
    public DeleteStickerUseCase deleteStickerUseCase() {
        return new DeleteStickerUseCaseImpl(stickerRepository, eventBus);
    }


    @Bean(name="bringFigureToFrontUseCase")
    public BringFigureToFrontUseCase bringFigureToFrontUseCase() {
        return new BringFigureToFrontUseCaseImpl(boardRepository, eventBus);
    }

    @Bean(name="sendFigureToBackUseCase")
    public SendFigureToBackUseCase sendFigureToBackUseCase() {
        return new SendFigureToBackUseCaseImpl(boardRepository, eventBus);
    }

    @Bean(name="moveCursorUseCase")
    public MoveCursorUseCase moveCursorUseCase() {
        return new MoveCursorUseCaseImpl(boardRepository, eventBus);
    }

    @Bean(name="showCursorUseCase")
    public ShowCursorUseCase showCursorUseCase() { return new ShowCursorUseCaseImpl(boardRepository, eventBus);
    }

    @Bean(name="enterBoardUseCase")
    public EnterBoardUseCase enterBoardUseCase() {
        return new EnterBoardUseCaseImpl(boardRepository, eventBus);
    }

    @Bean(name="leaveBoardUseCase")
    public LeaveBoardUseCase leaveBoardUseCase() {
        return new LeaveBoardUseCaseImpl(boardRepository, eventBus);
    }


    @Bean(name="createLineUseCase")
    public CreateLineUseCase createLineUseCase() {
        return new CreateLineUseCaseImpl(lineRepository, eventBus);
    }

    @Bean(name="deleteLineUseCase")
    public DeleteLineUseCase deleteLineUseCase() {
        return new DeleteLineUseCaseImpl(lineRepository, eventBus);
    }

    @Bean(name="moveLineUseCase")
    public MoveLineUseCase moveLineUseCase() {
        return new MoveLineUseCaseImpl(lineRepository, eventBus);
    }

    @Bean(name="changeTargetPositionUseCase")
    public ChangeTargetPositionUseCase changeTargetPositionUseCase() {
        return new ChangeTargetPositionUseCaseImpl(lineRepository, eventBus);
    }

    @Bean(name="changeSourcePositionUseCase")
    public ChangeSourcePositionUseCase changeSourcePositionUseCase() {
        return new ChangeSourcePositionUseCaseImpl(lineRepository, eventBus);
    }

    @Autowired
    public void setEventBus(DomainEventBus eventBus) { this.eventBus = eventBus; }

}
