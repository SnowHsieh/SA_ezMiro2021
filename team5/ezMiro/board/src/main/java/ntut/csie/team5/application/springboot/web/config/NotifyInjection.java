package ntut.csie.team5.application.springboot.web.config;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.team5.adapter.websocket.BoardSessionBroadcaster;
import ntut.csie.team5.usecase.board.BoardRepository;
import ntut.csie.team5.usecase.board.getcontent.GetBoardContentUseCase;
import ntut.csie.team5.usecase.eventhandler.NotifyBoard;
import ntut.csie.team5.usecase.eventhandler.NotifyBoardSessionBroadcaster;
import ntut.csie.team5.usecase.figure.connectable_figure.note.FigureRepository;
import ntut.csie.team5.usecase.websocket.WebSocketBroadcaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("EzMiroNotifyInjection")
public class NotifyInjection {

    private BoardRepository boardRepository;
    private FigureRepository figureRepository;
    private DomainEventBus eventBus;
    private GetBoardContentUseCase getBoardContentUseCase;
    private WebSocketBroadcaster webSocketBroadcaster;

    @Bean(name = "notifyBoard")
    public NotifyBoard notifyBoard() {
        return new NotifyBoard(boardRepository, eventBus);
    }

    @Bean(name = "notifyBoardSessionBroadcaster")
    public NotifyBoardSessionBroadcaster setNotifyBoardSessionBroadcaster() {
        return new NotifyBoardSessionBroadcaster(eventBus, getBoardContentUseCase, webSocketBroadcaster);
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
    public void setEventBus(DomainEventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Autowired
    public void setGetBoardContentUseCase(GetBoardContentUseCase getBoardContentUseCase) {
        this.getBoardContentUseCase = getBoardContentUseCase;
    }

    @Autowired
    public void setWebSocketBroadcaster(WebSocketBroadcaster webSocketBroadcaster) {
        this.webSocketBroadcaster = webSocketBroadcaster;
    }
}