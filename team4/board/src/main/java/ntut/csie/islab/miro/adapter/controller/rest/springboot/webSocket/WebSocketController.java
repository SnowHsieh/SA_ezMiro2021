//package ntut.csie.islab.miro.adapter.controller.rest.springboot.webSocket;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.springframework.stereotype.Component;
//
//import javax.websocket.*;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//
//@Component
//@ServerEndpoint(value = "/websocket/{usernick}")
//public class WebSocketController {
//
//    @OnOpen
//    public void onOpen(@PathParam(value = "usernick") String userNick,Session session) {
//        String message = "有新成員[" + userNick + "]加入CANVAS!";
//        System.out.println(message);
//
//        WebSocketUtil.addSession(userNick, session);
//        WebSocketUtil.sendMessageForAll(message);
//    }
//
//    @OnClose
//    public void onClose(@PathParam(value = "usernick") String userNick,Session session) throws IOException {
//        String message = "成員[" + userNick + "]退出聊天室!";
//        System.out.println(message);
//        WebSocketUtil.remoteSession(session);
//        session.close();
//        WebSocketUtil.sendMessageForAll(message);
//    }
//
//    @OnMessage
//    public void onMessage(@PathParam(value = "usernick") String userNick,String message, Session session) throws JSONException {
//        WebSocketUtil.setCursorOfUser(session, message);
//        WebSocketUtil.sendMessageToAllUser();
//    }
//
//    @OnError
//    public void onError(Session session, Throwable throwable) {
//        System.out.println("錯誤:" + throwable);
//        try {
//            session.close();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        throwable.printStackTrace();
//    }
//}
