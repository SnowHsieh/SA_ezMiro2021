package ntut.csie.selab.adapter.websocket;

import org.json.JSONObject;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BoardWebSocketImpl implements ntut.csie.selab.usecase.websocket.WebSocket {

    private final Map<String, Map<Session, String>> ONLINE_SESSION;

    public BoardWebSocketImpl() {
        this.ONLINE_SESSION = new ConcurrentHashMap<>();
    }

    // 新增紀錄Session
    public void addSessionIn(String boardId, String userId, Session session) {
        if (!ONLINE_SESSION.containsKey(boardId)) {
            ONLINE_SESSION.put(boardId, new HashMap<Session, String>() {{
                put(session, userId);
            }});
        } else {
            Map<Session, String> selectedBoard = ONLINE_SESSION.get(boardId);
            selectedBoard.put(session, userId);
        }
    }

    public void removeSessionFrom(String boardId, Session session) {
        Map<Session, String> selectedBoard = ONLINE_SESSION.get(boardId);
        selectedBoard.remove(session);
    }

    public void sendMessage(Session session, JSONObject message) {
        if (session == null) {
            return;
        }
        RemoteEndpoint.Async async = session.getAsyncRemote();
        async.sendText(message.toString());
    }

    public void sendMessageForAllUsersIn(String boardId, JSONObject message) {
        Map<Session, String> boardUsers = ONLINE_SESSION.get(boardId);
        boardUsers.forEach((session, userId) -> {
            sendMessage(session, message);
        });
    }
}

