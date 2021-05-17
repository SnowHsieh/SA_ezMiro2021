//package ntut.csie.islab.miro.adapter.controller.rest.springboot.webSocket;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import javax.websocket.RemoteEndpoint.Async;
//import javax.websocket.Session;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class WebSocketUtil {
//
//    private static final Map<Session,User> ONLINE_SESSION = new ConcurrentHashMap<>();
//
//    public static void addSession(String userNick, Session session) {
//       User user = new User(userNick, 0, 0);
//        ONLINE_SESSION.put(session, user);
//    }
//
//    public static void remoteSession(Session session) {
//        ONLINE_SESSION.remove(session);
//    }
//
//    public static void sendMessage(Session session, String message) {
//        if(session == null)
//            return;
//        System.out.println("current session" + session.getId());
//        Async async = session.getAsyncRemote();
//        async.sendText(message);
//    }
//
//    public static void sendMessageForAll(String message) {
//        ONLINE_SESSION.forEach((session,user) -> sendMessage(session, message));
//    }
//
//    public static void sendMessageToAllUser() {
//        ONLINE_SESSION.forEach((session, user) -> {
//            try {
//                if(session.isOpen()){
//                    sendMessage(session, getAllUserData());
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    public static void setCursorOfUser(Session session, String msg) {
//       User user = ONLINE_SESSION.get(session);
//        try {
//            JSONObject jsonObject = new JSONObject(msg);
//
//            user.setX(jsonObject.getJSONObject("position").getInt("x"));
//            user.setY(jsonObject.getJSONObject("position").getInt("y"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static String getAllUserData() throws JSONException{
//        JSONArray result = new JSONArray();
//        for (User user: ONLINE_SESSION.values()) {
//            JSONObject theUser = new JSONObject();
//            theUser.put("name", user.getName());
//            theUser.put("x", user.getX());
//            theUser.put("y", user.getY());
//            result.put(theUser);
//        }
//
//        return result.toString();
//    }
//}
//
//
