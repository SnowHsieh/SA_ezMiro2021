package ntut.csie.selab.adapter.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint(value = "/WebSocketServer/{usernick}")
public class WebSocketController {
    @OnOpen
    public void onOpen(@PathParam(value = "usernick") String usernick, Session session) {
        String message = "有新成員[" + usernick + "]加入看板!";
        System.out.println(message);
        WebSocketUtil.addSession(usernick, session);
        WebSocketUtil.sendMessageForAll(message);
    }

    @OnMessage
    public void OnMessage(@PathParam(value = "usernick") String usernick, String message) {
        String info = "成員[" + usernick + "]：" + message;
        System.out.println(info);
        WebSocketUtil.sendMessageForAll(message);
    }
}
