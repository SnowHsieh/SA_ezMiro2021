package ntut.csie.selab.application.springboot.web.config;

import ntut.csie.selab.adapter.websocket.BoardWebSocketImpl;
import ntut.csie.selab.usecase.board.create.CreateBoardUseCase;
import ntut.csie.selab.usecase.websocket.WebSocket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("MiroWebSocketInjection")
public class WebSocketInjection {

    @Bean(name="boardWebSocket")
    public WebSocket boardWebSocket() {
        return new BoardWebSocketImpl();
    }
}
