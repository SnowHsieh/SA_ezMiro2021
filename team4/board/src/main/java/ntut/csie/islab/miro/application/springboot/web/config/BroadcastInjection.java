package ntut.csie.islab.miro.application.springboot.web.config;

import ntut.csie.islab.miro.adapter.controller.rest.springboot.websocket.WebSocketBroadcaster;
import ntut.csie.islab.miro.usecase.websocket.BoardSessionBroadcaster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BroadcastInjection {

    @Bean(name="boardSessionBroadcaster")
    public BoardSessionBroadcaster createBoardSessionBroadcaster() {
        return new WebSocketBroadcaster();
    }
}