package ntut.csie.sslab.miro.application.springboot.web.config;

import ntut.csie.sslab.miro.adapter.controller.websocket.WebSocketBroadcaster;
import ntut.csie.sslab.miro.usecase.BoardSessionBroadcaster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BroadcastInjection {

    @Bean(name="boardSessionBroadcaster")
    public BoardSessionBroadcaster createBoardSessionBroadcaster() {
        return new WebSocketBroadcaster();
    }
}
