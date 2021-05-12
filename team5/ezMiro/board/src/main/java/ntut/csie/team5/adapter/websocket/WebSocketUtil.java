package ntut.csie.team5.adapter.websocket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.websocket.RemoteEndpoint.Async;
import javax.websocket.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketUtil {

    private static final Map<Session, User> ONLINE_SESSION = new ConcurrentHashMap<>();

    public static void addSession(String userNick, Session session) {
        User user = new User(userNick, 0, 0);
        ONLINE_SESSION.put(session, user);
    }

    public static void remoteSession(Session session) {
        ONLINE_SESSION.remove(session);
    }

    public static void sendMessage(Session session, String message) {
        if(session == null)
            return;

        Async async = session.getAsyncRemote();
        async.sendText(message);
    }

    public static void sendMessageForAll(String message) {
        ONLINE_SESSION.forEach((user, session) -> sendMessage(user, message));
    }

    public static void sendMessageToAllUser() {
        ONLINE_SESSION.forEach((session, user) -> {
            try {
                sendMessage(session, getAllUserData());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public static void setCursorOfUser(Session session, String pointerInfo) {
        User user = ONLINE_SESSION.get(session);
        try {
            JSONObject pointer = new JSONObject(pointerInfo);
            user.setX(pointer.getInt("x"));
            user.setY(pointer.getInt("y"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static String getAllUserData() throws JSONException{
        JSONArray result = new JSONArray();
        for (User user: ONLINE_SESSION.values()) {
            JSONObject theUser = new JSONObject();
            theUser.put("name", user.getName());
            theUser.put("x", user.getX());
            theUser.put("y", user.getY());
            result.put(theUser);
        }
        return result.toString();
    }
}


