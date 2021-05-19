package ntut.csie.selab.adapter.websocket;

import org.json.JSONObject;

import javax.websocket.RemoteEndpoint.Async;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketUtil {

    private static final Map<String, Map<Session, String>> ONLINE_SESSION = new ConcurrentHashMap<>();

    // 新增紀錄Session
    public static void addSessionIn(String boardId, String userId, Session session) {
        if (!ONLINE_SESSION.containsKey(boardId)) {
            ONLINE_SESSION.put(boardId, new HashMap<Session, String>() {{
                put(session, userId);
            }});
        } else {
            Map<Session, String> selectedBoard = ONLINE_SESSION.get(boardId);
            selectedBoard.put(session, userId);
        }
    }

    public static void removeSessionFrom(String boardId, Session session) {
        Map<Session, String> selectedBoard = ONLINE_SESSION.get(boardId);
        selectedBoard.remove(session);
    }

    public static void sendMessage(Session session, JSONObject message) {
        if (session == null) {
            return;
        }
        Async async = session.getAsyncRemote();
        async.sendText(message.toString());
    }

    public static void sendMessageForAllUsersIn(String boardId, JSONObject message) {
        Map<Session, String> boardUsers= ONLINE_SESSION.get(boardId);
        boardUsers.forEach((session, userId) -> {
            sendMessage(session, message);
        });
    }
}

