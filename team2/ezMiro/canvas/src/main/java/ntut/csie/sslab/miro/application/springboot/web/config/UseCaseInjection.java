package ntut.csie.sslab.miro.application.springboot.web.config;

import ntut.csie.sslab.ddd.model.DomainEventBus;
//import ntut.csie.sslab.miro.adapter.controller.websocket.BoardSessionWebSocketAdapter;
import ntut.csie.sslab.miro.adapter.gateway.eventbus.NotifyBoardAdapter;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.board.CreateBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.CreateBoardUseCaseImpl;
import ntut.csie.sslab.miro.usecase.eventhandler.NotifyBoard;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.delete.DeleteNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.delete.DeleteNoteUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.edit.color.ChangeNoteColorUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.color.ChangeNoteColorUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.edit.description.ChangeNoteDescriptionUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.description.ChangeNoteDescriptionUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.edit.zorder.back.SendNoteToBackUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.zorder.back.SendNoteToBackUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.edit.zorder.front.BringNoteToFrontUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.zorder.front.BringNoteToFrontUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.edit.size.ChangeNoteSizeUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.size.ChangeNoteSizeUseCaseImpl;
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
    private DomainEventBus eventBus;
    private ExecutorService executor;

    @Bean(name="createBoardUseCase")
    public CreateBoardUseCase createBoardUseCase() {
        return new CreateBoardUseCaseImpl(boardRepository, eventBus);
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

    @Bean(name="notifyBoardAdapter")
    public NotifyBoardAdapter notifyBoardAdapter() {
        return new NotifyBoardAdapter(new NotifyBoard(figureRepository, boardRepository, eventBus));
    }

//    @Bean(name="boardSessionWebSocketAdapter")
//    public BoardSessionWebSocketAdapter boardSessionWebSocketAdapter() {
//        return new BoardSessionWebSocketAdapter();
//    }

    @Bean(name="deleteNoteUseCase")
    public DeleteNoteUseCase deleteNoteUseCase() {
        return new DeleteNoteUseCaseImpl(figureRepository, eventBus);
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
    public void setEventBus(DomainEventBus eventBus) { this.eventBus = eventBus; }
}