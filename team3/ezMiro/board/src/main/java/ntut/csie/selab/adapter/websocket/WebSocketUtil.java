package ntut.csie.selab.adapter.websocket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.websocket.RemoteEndpoint.Async;
import javax.websocket.Session;
import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketUtil {

    private static final Map<Session, User> ONLINE_SESSION = new ConcurrentHashMap<>();

    // 新增紀錄Session
    public static void addSession(String userNick, Session session) {
        User user = new User(userNick, 0, 0);
        ONLINE_SESSION.put(session, user);
    }

    // 移除Session
    public static void removeSession(Session session) {
        ONLINE_SESSION.remove(session);
    }

    // 發送訊息
    public static void sendMessage(Session session, String message) {
        System.out.println("users: "+ message + ", session: "+ session);
        if (session == null) {
            return;
        }
        Async async = session.getAsyncRemote();
        async.sendText(message);
    }

    // 發送群體訊息
    public static void sendUsersForAll() {
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
            user.x = Integer.parseInt(pointer.get("x").toString());
            user.y = Integer.parseInt(pointer.get("y").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static String getAllUserData () throws JSONException {
        JSONArray result = new JSONArray();
        for (User user: ONLINE_SESSION.values()) {
            JSONObject theUser = new JSONObject();
            theUser.put("name", user.name);
            theUser.put("x", user.x);
            theUser.put("y", user.y);
            result.put(theUser);
        }
        return result.toString();
    }



//    public static Cursor stringToCursor(String cursorInfo) {
//        int x = 0;
//        int y = 0;
//
//        try {
//            JSONObject cursorJSON = new JSONObject(cursorInfo);
//            x = cursorJSON.getInt("x");
//            y = cursorJSON.getInt("y");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return new Cursor(x, y);
//    }
}

class User {
    public String name;
    public int x;
    public int y;

    public User(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.append("name", name);
        jsonObject.append("x", x);
        jsonObject.append("y", y);
        return jsonObject.toString();
    }
}