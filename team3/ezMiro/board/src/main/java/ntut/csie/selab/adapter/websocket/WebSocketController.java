package ntut.csie.selab.adapter.websocket;

import ntut.csie.selab.usecase.board.EnterBoardInput;
import ntut.csie.selab.usecase.board.EnterBoardOutput;
import ntut.csie.selab.usecase.board.EnterBoardUseCase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint(value = "/WebSocketServer/{boardId}/{userId}")
public class WebSocketController {
    private static ApplicationContext applicationContext ;

    private EnterBoardUseCase enterBoardUseCase;

    public static void setApplicationContext (ApplicationContext applicationContext) {
        WebSocketController. applicationContext = applicationContext ;
    }

    @OnOpen
    public void onOpen(@PathParam(value = "boardId") String boardId, @PathParam(value = "userId") String userId, Session session) throws JSONException {
        enterBoardUseCase = applicationContext.getBean(EnterBoardUseCase.class);
        EnterBoardInput input = new EnterBoardInput();
        EnterBoardOutput output = new EnterBoardOutput();
        input.setBoardId(boardId);
        input.setUserId(userId);
        enterBoardUseCase.execute(input, output);

        String msg = "有新成員[" + userId + "]加入看板!";
        System.out.println(msg);
        WebSocketUtil.addSession(userId, session);

        JSONArray parsedCursors = new JSONArray();
        output.getCursor().forEach(cursor -> {
            JSONObject parsedCursor = new JSONObject();
            try {
                parsedCursor.put("userId", cursor.getUserId());
                parsedCursor.put("x", cursor.getCoordinate().x);
                parsedCursor.put("y", cursor.getCoordinate().y);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            parsedCursors.put(parsedCursor);
        });
        JSONObject message = new JSONObject();
        message.put("cursors", parsedCursors);
        WebSocketUtil.sendUsersForAll(message);
    }

    @OnMessage
    public void OnMessage(@PathParam(value = "userId") String usernick, String message, Session session) {
        String info = "mouse moved: 成員[" + usernick + "]：" + message;
        System.out.println(info);

    }

    @OnClose
    public void OnClose(@PathParam(value = "userId") String usernick, Session session) {
        WebSocketUtil.removeSession(session);
        String info = "成員[" + usernick + "]：連線已斷開" ;
        System.out.println(info);
    }
}
