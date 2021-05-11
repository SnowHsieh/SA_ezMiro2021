package ntut.csie.sslab.kanban.application.springboot.web.config;

import ntut.csie.sslab.kanban.adapter.controller.websocket.WebSocketBroadcaster;
import ntut.csie.sslab.kanban.usecase.BoardSessionBroadcaster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BroadcastInjection {
    @Bean(name="boardSessionBroadcaster")
    public BoardSessionBroadcaster createBoardSessionBroadcaster() {
        return new WebSocketBroadcaster();
    }
}
