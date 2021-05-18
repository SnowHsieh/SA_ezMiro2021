package ntut.csie.selab.adapter.websocket;

import ntut.csie.selab.usecase.board.EnterBoardOutput;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.websocket.RemoteEndpoint.Async;
import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketUtil {

    private static final Map<Session, String> ONLINE_SESSION = new ConcurrentHashMap<>();

    // 新增紀錄Session
    public static void addSession(String userId, Session session) {
        ONLINE_SESSION.put(session, userId);
    }

    // 移除Session
    public static void removeSession(Session session) {
        ONLINE_SESSION.remove(session);
    }

    // 發送訊息
    public static void sendMessage(Session session, JSONObject message) {
        System.out.println("users: "+ message + ", session: "+ session);
        if (session == null) {
            return;
        }
        Async async = session.getAsyncRemote();
        async.sendText(message.toString());
    }

    // 發送群體訊息
    public static void sendUsersForAll(JSONObject message) {
        ONLINE_SESSION.forEach((session, userId) -> {
            sendMessage(session, message);
        });
    }
}

