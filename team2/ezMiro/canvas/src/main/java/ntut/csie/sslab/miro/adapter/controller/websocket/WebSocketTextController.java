package ntut.csie.sslab.miro.adapter.controller.websocket;

import ntut.csie.sslab.miro.usecase.note.OnlineUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WebSocketTextController {

    private Map<String, OnlineUserDTO> allOnlineUsers = new HashMap<>();

    @Autowired
    SimpMessagingTemplate template;

    @MessageMapping("/sendMessage")
    public void receiveMessage(@Payload OnlineUserDTO onlineUserDTO, @Header("simpSessionId") String sessionId) {
        allOnlineUsers.put(sessionId, onlineUserDTO);
        template.convertAndSend("/topic/position", allOnlineUsers.values());
    }

    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        allOnlineUsers.remove(event.getSessionId());
    }
}