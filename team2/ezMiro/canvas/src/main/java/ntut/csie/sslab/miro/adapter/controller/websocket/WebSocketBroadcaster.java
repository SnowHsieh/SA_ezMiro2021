//package ntut.csie.sslab.miro.adapter.controller.websocket;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import javax.websocket.EncodeException;
//import javax.websocket.RemoteEndpoint;
//import javax.websocket.Session;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//import java.util.concurrent.ConcurrentHashMap;
//
//
//public class WebSocketBroadcaster {
//    private Map<String, Session> sessionMap;
//
//    public WebSocketBroadcaster() {
//        sessionMap = new HashMap<>();
//    }
//
//    private static final Map<Session, User> ONLINE_SESSION = new ConcurrentHashMap<>();
//
//    // 新增紀錄Session
//    public static void addSession(String userId, Session session) {
//        User user = new User(userId, 0, 0);
//        ONLINE_SESSION.put(session, user);
//    }
//
//    // 移除Session
//    public static void removeSession(Session session) {
//        ONLINE_SESSION.remove(session);
//    }
//
//    // 發送訊息
//    public static void sendMessage(Session session, String message) {
//        System.out.println("users: "+ message + ", session: "+ session);
//        if (session == null) {
//            return;
//        }
//        RemoteEndpoint.Async async = session.getAsyncRemote();
//        async.sendText(message);
//    }
//
//    // 發送群體訊息
//    public static void sendMessageForAll(String message) {
//        ONLINE_SESSION.forEach((session, user) -> {
//            try {
//                sendMessage(session, getAllUserData());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    private static String getAllUserData () throws JSONException {
//        JSONArray result = new JSONArray();
//        for (User user: ONLINE_SESSION.values()) {
//            JSONObject targetUser = new JSONObject();
//            targetUser.put("name", user.userId);
//            targetUser.put("x", user.x);
//            targetUser.put("y", user.y);
//            result.put(targetUser);
//        }
//        return result.toString();
//    }
//
//    static class User {
//        public String userId;
//        public int x;
//        public int y;
//
//        public User(String userId, int x, int y) {
//            this.userId = userId;
//            this.x = x;
//            this.y = y;
//        }
//
//        public String toJSON() throws JSONException {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.append("userId", userId);
//            jsonObject.append("x", x);
//            jsonObject.append("y", y);
//            return jsonObject.toString();
//        }
//    }
//
////
////    @Override
////    public void broadcast(RemoteDomainEvent remoteDomainEvent, String boardSessionId) {
////        Session session = sessionMap.get(boardSessionId);
////        if(session == null)
////            return;
////
////        synchronized (session) {
////            try {
////                session.getBasicRemote().sendObject(remoteDomainEvent);
////
////            } catch (IOException | IllegalStateException | EncodeException e) {
////                e.printStackTrace();
////            }
////        }
////    }
////
////    public void addSession(String boardSessionId, Session session) {
////        this.sessionMap.put(boardSessionId, session);
////    }
////
////    public void removeSession(String boardSessionId) {
////        sessionMap.remove(boardSessionId);
////    }
////
////    public String getBoardSessionIdBySessionId(String sessionId){
////        for(String boardSessionId: sessionMap.keySet()){
////            if(sessionMap.get(boardSessionId).getId().equals(sessionId)){
////                return boardSessionId;
////            }
////        }
////        throw new RuntimeException("sessionId: " + sessionId +" not found!");
////    }
//}
