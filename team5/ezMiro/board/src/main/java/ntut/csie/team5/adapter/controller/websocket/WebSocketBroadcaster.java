package ntut.csie.team5.adapter.controller.websocket;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.team5.usecase.board.BoardSessionBroadcaster;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketBroadcaster implements BoardSessionBroadcaster {

    private final Map<String, Session> boardSessionMap;

    public WebSocketBroadcaster() {
        boardSessionMap = new ConcurrentHashMap<>();
    }

    @Override
    public void broadcast(String boardSessionId, DomainEvent domainEvent) {
        Session session = boardSessionMap.get(boardSessionId);
        if (null == session) {
            return;
        }

        session.getAsyncRemote().sendObject(domainEvent);
    }

    public void addSession(String boardSessionId, Session session) {
        session.setMaxIdleTimeout(30 * 60);
        boardSessionMap.put(boardSessionId, session);
    }

    public void removeSession(String boardSessionId) {
        boardSessionMap.remove(boardSessionId);
    }

    public String getBoardSessionIdBySessionId(String sessionId) {
        Map.Entry<String, Session> boardSession = boardSessionMap.entrySet().stream()
                .filter(ses -> ses.getValue().getId().equals(sessionId)).findAny().orElse(null);

        return boardSession.getKey();
    }
}
