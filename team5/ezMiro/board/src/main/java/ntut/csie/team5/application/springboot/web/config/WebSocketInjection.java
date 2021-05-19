package ntut.csie.team5.application.springboot.web.config;

import ntut.csie.team5.adapter.websocket.BoardSessionBroadcaster;
import ntut.csie.team5.usecase.websocket.WebSocketBroadcaster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("EzMiroWebSocketInjection")
public class WebSocketInjection {

    @Bean(name = "webSocketBroadcaster")
    public WebSocketBroadcaster webSocketBroadcaster() {
        return new BoardSessionBroadcaster();
    }
}
