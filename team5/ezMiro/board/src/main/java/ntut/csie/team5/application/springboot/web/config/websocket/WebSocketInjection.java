package ntut.csie.team5.application.springboot.web.config.websocket;

import ntut.csie.team5.adapter.controller.websocket.WebSocketBroadcaster;
import ntut.csie.team5.usecase.board.BoardSessionBroadcaster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("EzMiroWebSocketInjection")
public class WebSocketInjection {

    @Bean(name = "boardSessionBroadcaster")
    public BoardSessionBroadcaster boardSessionBroadcaster() {
        return new WebSocketBroadcaster();
    }
}
