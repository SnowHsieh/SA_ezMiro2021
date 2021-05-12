//package ntut.csie.sslab.miro.adapter.controller.websocket;
//
//
//import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.websocket.OnClose;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//
//
//@ServerEndpoint(value = "/ezmiro/miro/users/")
//public class BoardSessionWebSocketAdapter {
//
//    @OnMessage
//    public void onMessage(String message, @PathParam("userId") String userId)  {
//        String info = "成員[" + userId + "]：" + message;
//        System.out.println(info);
//        WebSocketBroadcaster.sendMessageForAll(message);
//    }
//
//    @OnOpen
//    public void onOpen(Session session, @PathParam("userId") String userId) {
//        String message =  userId + "join!";
//        System.out.println(message);
//        WebSocketBroadcaster.addSession(userId, session);
//        WebSocketBroadcaster.sendMessageForAll(message);
//    }
//
//    @OnClose
//    public void onClose(Session session) {
//        WebSocketBroadcaster.removeSession(session);
//    }
//}
