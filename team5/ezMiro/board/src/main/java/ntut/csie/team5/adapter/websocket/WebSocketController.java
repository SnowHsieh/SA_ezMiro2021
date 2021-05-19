package ntut.csie.team5.adapter.websocket;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.entity.model.board.Cursor;
import ntut.csie.team5.entity.model.websocket.MessageType;
import ntut.csie.team5.usecase.board.BoardRepository;
import ntut.csie.team5.usecase.board.enter.EnterBoardInput;
import ntut.csie.team5.usecase.board.enter.EnterBoardUseCase;
import ntut.csie.team5.usecase.board.leave.LeaveBoardInput;
import ntut.csie.team5.usecase.board.leave.LeaveBoardUseCase;
import ntut.csie.team5.usecase.board.move_cursor.MoveCursorInput;
import ntut.csie.team5.usecase.board.move_cursor.MoveCursorUseCase;
import ntut.csie.team5.usecase.websocket.WebSocketBroadcaster;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;

@Component
@ServerEndpoint(value = "/WebSocketServer/{boardId}/{userId}")
public class WebSocketController {

    private static ApplicationContext applicationContext;

    private BoardRepository boardRepository;
    private EnterBoardUseCase enterBoardUseCase;
    private LeaveBoardUseCase leaveBoardUseCase;
    private MoveCursorUseCase moveCursorUseCase;
    private WebSocketBroadcaster webSocketBroadcaster;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocketController.applicationContext = applicationContext;
    }

    public WebSocketController() {
    }

    public void setBoardRepository(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void setEnterBoardUseCase(EnterBoardUseCase enterBoardUseCase) {
        this.enterBoardUseCase = enterBoardUseCase;
    }

    public void setLeaveBoardUseCase(LeaveBoardUseCase leaveBoardUseCase) {
        this.leaveBoardUseCase = leaveBoardUseCase;
    }

    public void setMoveCursorUseCase(MoveCursorUseCase moveCursorUseCase) {
        this.moveCursorUseCase = moveCursorUseCase;
    }

    public void setWebSocketBroadcaster(WebSocketBroadcaster webSocketBroadcaster) {
        this.webSocketBroadcaster = webSocketBroadcaster;
    }

    @OnOpen
    public void onOpen(@PathParam(value = "boardId") String boardId, @PathParam(value = "userId") String userId, Session session) {
        enterBoardUseCase = applicationContext.getBean(EnterBoardUseCase.class);
        webSocketBroadcaster = applicationContext.getBean(WebSocketBroadcaster.class);

        EnterBoardInput input = enterBoardUseCase.newInput();
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        input.setBoardId(boardId);
        input.setUserId(userId);

        enterBoardUseCase.execute(input, presenter);

        String message = "有新成員[" + userId + "]加入畫布!";
        System.out.println(message);
        webSocketBroadcaster.addSession(boardId, presenter.getId(), session);
        webSocketBroadcaster.sendMessageToAllUser(boardId, message);
    }

    @OnClose
    public void onClose(@PathParam(value = "boardId") String boardId, @PathParam(value = "userId") String userId, Session session) {
        leaveBoardUseCase = applicationContext.getBean(LeaveBoardUseCase.class);
        webSocketBroadcaster = applicationContext.getBean(WebSocketBroadcaster.class);

        LeaveBoardInput input = leaveBoardUseCase.newInput();
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        String boardSessionId = ((BoardSessionBroadcaster) webSocketBroadcaster).getBoardSessionIdBySessionId(boardId, session.getId());

        input.setBoardId(boardId);
        input.setUserId(userId);
        input.setBoardSessionId(boardSessionId);

        leaveBoardUseCase.execute(input, presenter);

        String message = "成員[" + userId + "]退出畫布!";
        System.out.println(message);
        webSocketBroadcaster.removeSession(boardId, session);
        webSocketBroadcaster.sendMessageToAllUser(boardId, message);
    }

    @OnMessage
    public void onMessage(@PathParam(value = "boardId") String boardId, @PathParam(value = "userId") String userId, String message, Session session) {
        moveCursorUseCase = applicationContext.getBean(MoveCursorUseCase.class);
        boardRepository = applicationContext.getBean(BoardRepository.class);
        webSocketBroadcaster = applicationContext.getBean(WebSocketBroadcaster.class);

        MoveCursorInput input = moveCursorUseCase.newInput();
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        input.setBoardId(boardId);
        input.setUserId(userId);

        try {
            JSONObject pointer = new JSONObject(message);
            input.setPositionX(pointer.getInt("x"));
            input.setPositionY(pointer.getInt("y"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        moveCursorUseCase.execute(input, presenter);

        Board board = boardRepository.findById(boardId).orElse(null);
        List<Cursor> cursors = board.getCursors();

        try {
            JSONObject messageJSON = new JSONObject();
            messageJSON.put("type", MessageType.CURSOR);

            JSONArray cursorsJsonArray = new JSONArray();
            for (Cursor cursor: cursors) {
//                if(cursor.userId().equals(userId))
//                    continue;

                JSONObject cursorJson = new JSONObject();
                cursorJson.put("name", cursor.userId());
                cursorJson.put("x", cursor.positionX());
                cursorJson.put("y", cursor.positionY());

                cursorsJsonArray.put(cursorJson);
            }
            messageJSON.put("cursors", cursorsJsonArray);
            webSocketBroadcaster.sendMessageToAllUser(boardId, messageJSON.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("錯誤:" + throwable);
        try {
            session.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        throwable.printStackTrace();
    }
}
