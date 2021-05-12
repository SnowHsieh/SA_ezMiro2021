package ntut.csie.sslab.kanban.adapter.controller.websocket;


import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.kanban.usecase.BoardSessionBroadcaster;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class WebSocketBroadcaster implements BoardSessionBroadcaster {
    private Map<String, Session> sessionMap;

    public WebSocketBroadcaster() {
        sessionMap = new HashMap<>();
    }

    @Override
    public void broadcast(DomainEvent domainEvent, String sessionId) {
        Session session = sessionMap.get(sessionId);
        if(session == null)
            return;

        synchronized (session) {
            try {
                session.getBasicRemote().sendObject(domainEvent);

            } catch (IOException | IllegalStateException | EncodeException e) {
                e.printStackTrace();
            }
        }
    }

    public void addSession(String boardSessionId, Session session) {
        this.sessionMap.put(boardSessionId, session);
    }

    public void removeSession(String cursorId) {
        sessionMap.remove(cursorId);
    }

    public String getBoardSessionIdBySessionId(String sessionId){
        for(String boardSessionId: sessionMap.keySet()){
            if(sessionMap.get(boardSessionId).getId().equals(sessionId)){
                return boardSessionId;
            }
        }
        throw new RuntimeException("sessionId: " + sessionId +" not found!");
    }
}
