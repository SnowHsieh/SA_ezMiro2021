package ntut.csie.team5.usecase.websocket;

import javax.websocket.Session;

public interface WebSocketBroadcaster {

    void addSession(String boardId, String boardSessionId, Session session);

    void removeSession(String boardId,Session session);

    void sendMessage(Session session, String message);

    void sendMessageToAllUser(String boardId, String message);
}
