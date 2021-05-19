package ntut.csie.selab.adapter.websocket;

import ntut.csie.selab.entity.model.board.Cursor;
import ntut.csie.selab.usecase.board.leaveboard.LeaveBoardInput;
import ntut.csie.selab.usecase.board.leaveboard.LeaveBoardOutput;
import ntut.csie.selab.usecase.board.leaveboard.LeaveBoardUseCase;
import ntut.csie.selab.usecase.board.movecursor.MoveCursorInput;
import ntut.csie.selab.usecase.board.movecursor.MoveCursorOutput;
import ntut.csie.selab.usecase.board.movecursor.MoveCursorUseCase;
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
import java.awt.Point;
import java.util.Set;

@Component
@ServerEndpoint(value = "/WebSocketServer/{boardId}/{userId}")
public class WebSocketController {
    private static ApplicationContext applicationContext ;

    private MoveCursorUseCase moveCursorUseCase;
    private LeaveBoardUseCase leaveBoardUseCase;

    public static void setApplicationContext (ApplicationContext applicationContext) {
        WebSocketController. applicationContext = applicationContext ;
    }

    @OnOpen
    public void onOpen(@PathParam(value = "boardId") String boardId, @PathParam(value = "userId") String userId, Session session) throws JSONException {

        String msg = "有新成員[" + userId + "]加入看板!";
        System.out.println(msg);
        WebSocketUtil.addSessionIn(boardId, userId, session);
    }

    @OnMessage
    public void OnMessage(@PathParam(value = "boardId") String boardId, @PathParam(value = "userId") String userId, String message, Session session) {
        try {
            JSONObject messageJSON = new JSONObject(message);
            if (!messageJSON.isNull("widgets")) {
                processWidgetMessage(messageJSON, boardId, userId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void processWidgetMessage(JSONObject message, String boardId, String usernick) {
        String info = "widget changed or created: 成員[" + usernick + "]：" + message + "in board：" + boardId;
        WebSocketUtil.sendMessageForAllUsersIn(boardId, message);
        System.out.println(info);
    }

    @OnClose
    public void OnClose(@PathParam(value = "boardId") String boardId, @PathParam(value = "userId") String usernick, Session session) {
        leaveBoardUseCase = applicationContext.getBean(LeaveBoardUseCase.class);
        LeaveBoardInput input= new LeaveBoardInput();
        LeaveBoardOutput output = new LeaveBoardOutput();
        input.setBoardId(boardId);
        input.setUserId(usernick);
        leaveBoardUseCase.execute(input, output);
        WebSocketUtil.removeSessionFrom(boardId, session);
        WebSocketUtil.sendMessageForAllUsersIn(boardId, convertCursorToMessage(output.getCursors()));
        String info = "Board[" + boardId + "]中成員[" + usernick + "]的連線已斷開" ;
        System.out.println(info);
    }

    private JSONObject convertCursorToMessage(Set<Cursor> cursorSet) {
        JSONArray parsedCursors = new JSONArray();
        cursorSet.forEach(cursor -> {
            JSONObject parsedCursor = new JSONObject();
            try {
                parsedCursor.put("userId", cursor.getUserId());
                parsedCursor.put("x", cursor.getPoint().x);
                parsedCursor.put("y", cursor.getPoint().y);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            parsedCursors.put(parsedCursor);
        });

        JSONObject message = new JSONObject();
        try {
            message.put("cursors", parsedCursors);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }
}
