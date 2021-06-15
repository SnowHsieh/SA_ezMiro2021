package ntut.csie.selab.adapter.websocket;

import ntut.csie.selab.entity.model.board.Cursor;
import ntut.csie.selab.usecase.board.leaveboard.LeaveBoardInput;
import ntut.csie.selab.usecase.board.leaveboard.LeaveBoardOutput;
import ntut.csie.selab.usecase.board.leaveboard.LeaveBoardUseCase;
import ntut.csie.selab.usecase.websocket.WebSocket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Set;

@Component
@ServerEndpoint(value = "/WebSocketServer/{boardId}/{userId}")
public class BoardWebSocketController {
    private static ApplicationContext applicationContext ;
    private LeaveBoardUseCase leaveBoardUseCase;
    private WebSocket boardWebSocket;

    public static void setApplicationContext (ApplicationContext applicationContext) {
        BoardWebSocketController.applicationContext = applicationContext;
    }

    @OnOpen
    public void onOpen(@PathParam(value = "boardId") String boardId, @PathParam(value = "userId") String userId, Session session) throws JSONException {
        boardWebSocket = applicationContext.getBean(BoardWebSocketImpl.class);
        String msg = "有新成員[" + userId + "]加入看板!";
        System.out.println(msg);
        boardWebSocket.addSessionIn(boardId, userId, session);
    }

    @OnMessage
    public void OnMessage(@PathParam(value = "boardId") String boardId, @PathParam(value = "userId") String userId, String message, Session session) {
//        try {
//            JSONObject messageJSON = new JSONObject(message);
//            if (!messageJSON.isNull("widgets")) {
//                processWidgetMessage(messageJSON, boardId, userId);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    private void processWidgetMessage(JSONObject message, String boardId, String usernick) {
        boardWebSocket = applicationContext.getBean(BoardWebSocketImpl.class);
        String info = "widget changed or created: 成員[" + usernick + "]：" + message + "in board：" + boardId;
        boardWebSocket.sendMessageForAllUsersIn(boardId, message);
        System.out.println(info);
    }

    @OnClose
    public void OnClose(@PathParam(value = "boardId") String boardId, @PathParam(value = "userId") String usernick, Session session) {
        boardWebSocket = applicationContext.getBean(BoardWebSocketImpl.class);
        leaveBoardUseCase = applicationContext.getBean(LeaveBoardUseCase.class);
        LeaveBoardInput input= new LeaveBoardInput();
        LeaveBoardOutput output = new LeaveBoardOutput();
        input.setBoardId(boardId);
        input.setUserId(usernick);
        boardWebSocket.removeSessionFrom(boardId, session);
        leaveBoardUseCase.execute(input, output);
        String info = "Board[" + boardId + "]中成員[" + usernick + "]的連線已斷開" ;
        System.out.println(info);
    }
}
