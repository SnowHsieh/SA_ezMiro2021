package ntut.csie.selab.usecase.websocket;

import org.json.JSONObject;

import javax.websocket.Session;

public interface WebSocket {

    void addSessionIn(String boardId, String userId, Session session);
    void removeSessionFrom(String boardId, Session session);
    void sendMessage(Session session, JSONObject message);
    void sendMessageForAllUsersIn(String boardId, JSONObject message);
}
