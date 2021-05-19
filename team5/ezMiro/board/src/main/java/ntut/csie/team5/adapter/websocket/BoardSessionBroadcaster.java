package ntut.csie.team5.adapter.websocket;

import ntut.csie.team5.usecase.websocket.WebSocketBroadcaster;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BoardSessionBroadcaster implements WebSocketBroadcaster {

    private final Map<String, Map<Session, String>> boardSessionMap;

    public BoardSessionBroadcaster() {
        this.boardSessionMap = new HashMap<>();
    }

    public void addSession(String boardId, String boardSessionId, Session session) {
        if (!boardSessionMap.containsKey(boardId)) {
            boardSessionMap.put(boardId, new ConcurrentHashMap<>());
        }
        Map<Session, String> sessionStringMap = boardSessionMap.get(boardId);
        sessionStringMap.put(session, boardSessionId);
    }

    public void removeSession(String boardId, Session session) {
        if (!boardSessionMap.containsKey(boardId)) {
            return;
        }

        Map<Session, String> sessionStringMap = boardSessionMap.get(boardId);
        sessionStringMap.remove(session);

        if (sessionStringMap.size() == 0) {
            boardSessionMap.remove(boardId);
        }
    }

    public String getBoardSessionIdBySessionId(String boardId, String sessionId) {
        Map<Session, String> sessionStringMap = boardSessionMap.get(boardId);

        Map.Entry<Session, String> session = sessionStringMap.entrySet().stream().filter(ses -> ses.getKey().getId().equals(sessionId)).findAny().orElse(null);

        return session.getValue();
    }

    public void sendMessage(Session session, String message) {
        if(session == null)
            return;

        RemoteEndpoint.Async async = session.getAsyncRemote();
        async.sendText(message);
    }

    public void sendMessageToAllUser(String boardId, String message) {
        Map<Session, String> sessionStringMap = boardSessionMap.get(boardId);

        sessionStringMap.forEach((session, boardSessionId) -> sendMessage(session, message));
    }
}
