package ntut.csie.islab.miro.adapter.controller.rest.springboot.websocket;

import ntut.csie.islab.miro.usecase.websocket.BoardSessionBroadcaster;
import ntut.csie.sslab.ddd.model.DomainEvent;
import org.json.JSONObject;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketBroadcaster implements BoardSessionBroadcaster {
    private static final Map<String, Session> ONLINE_SESSION = new ConcurrentHashMap<>();
    public WebSocketBroadcaster() {
    }

    @Override
    public void broadcast(DomainEvent domainEvent, String userId) {
        Session session = ONLINE_SESSION.get(userId);
        if(session == null)
            return;

        synchronized (session) {
            try {
                if(session.isOpen()){
                    session.getAsyncRemote().sendObject(domainEvent); // getBasicRemote -> getAsyncRemote
                }

            } catch ( IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }
    public void addSession(String boardSessionId, Session session) {
        this.ONLINE_SESSION.put(boardSessionId, session);
    }
    public void removeSession(String boardSessionId) {
        ONLINE_SESSION.remove(boardSessionId);
    }
    public String getBoardSessionIdBySessionId(String sessionId){
        for(String boardSessionId: ONLINE_SESSION.keySet()){
            if(ONLINE_SESSION.get(boardSessionId).getId().equals(sessionId)){
                return boardSessionId;
            }
        }
        throw new RuntimeException("sessionId: " + sessionId +" not found!");
    }

//    public void narrowCastMsg(JSONObject obj, Session session) {
//        synchronized (session) {
//            try {
//                if(session.isOpen()){
//                    session.getAsyncRemote().sendText(obj.toString());
//                }
//            } catch ( Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
