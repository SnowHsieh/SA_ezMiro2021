package ntut.csie.islab.miro.adapter.controller.rest.springboot.webSocket;

import ntut.csie.islab.miro.adapter.presenter.broadcastDomainEvent.DomainEventEncoder;
import ntut.csie.islab.miro.application.springboot.web.config.websocket.EndpointConfigure;
import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.board.cursor.event.CursorMovedDomainEvent;
import ntut.csie.islab.miro.usecase.board.EnterBoardInput;
import ntut.csie.islab.miro.usecase.board.EnterBoardUseCase;
import ntut.csie.islab.miro.usecase.board.LeaveBoardInput;
import ntut.csie.islab.miro.usecase.board.LeaveBoardUseCase;
import ntut.csie.islab.miro.usecase.board.cursor.MoveCursorInput;
import ntut.csie.islab.miro.usecase.board.cursor.MoveCursorUseCase;
import ntut.csie.islab.miro.usecase.webSocket.BoardSessionBroadcaster;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.UUID;

@ServerEndpoint(value = "/websocket/{boardId}/{userId}",
        encoders = {DomainEventEncoder.class},
        configurator = EndpointConfigure.class)
@Component
public class BoardSessionWebSocketAdapter {

    private MoveCursorUseCase moveCursorUseCase;
    private EnterBoardUseCase enterBoardUseCase;
    private LeaveBoardUseCase leaveBoardUseCase;
    private BoardSessionBroadcaster boardSessionBroadcaster;

    @Autowired
    public void setMoveCursorUseCase(MoveCursorUseCase moveCursorUseCase) {
        this.moveCursorUseCase = moveCursorUseCase;
    }

    @Autowired
    public void setEnterBoardUseCase(EnterBoardUseCase enterBoardUseCase) {
        this.enterBoardUseCase = enterBoardUseCase;
    }

    @Autowired
    public void setLeaveBoardUseCase(LeaveBoardUseCase leaveBoardUseCase) {
        this.leaveBoardUseCase = leaveBoardUseCase;
    }

    @Autowired
    public void setBoardSessionBroadcaster(BoardSessionBroadcaster boardSessionBroadcaster) {
        this.boardSessionBroadcaster = boardSessionBroadcaster;
    }

    @OnMessage
    public void onMessage(String jsonString, Session session)  {
        String event = "";
        JSONObject info;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject = jsonObject.getJSONObject("message");
            event = jsonObject.getString("event");
            info = jsonObject.getJSONObject("info");
            System.out.println("event in onMessage is: "+jsonObject.toString());
            websocketEventHandler(event, info);
        }catch (JSONException err){
            System.out.println(err);
        }

    }

    private void websocketEventHandler(String event, JSONObject info) {
        if(CursorMovedDomainEvent.class.getSimpleName().equals(event)){
            handleCursorMoved(info);

        }
    }

    @OnOpen
    public void onOpen(Session session,
                       @PathParam("boardId") String boardId,
                       @PathParam("userId") String userId) throws IOException {
        System.out.println(userId.toString() + "加入了戰局");
        EnterBoardInput input = enterBoardUseCase.newInput();
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        input.setUserId(UUID.fromString(userId));
        input.setBoardId(UUID.fromString(boardId));

        enterBoardUseCase.execute(input, presenter);

        ((WebSocketBroadcaster)boardSessionBroadcaster).addSession(presenter.getId(), session);

        WebSocketBroadcaster webSocketBroadcaster = new WebSocketBroadcaster();
        webSocketBroadcaster.broadcast2();
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println(session.getId() + "離開了戰局");
        String boardSessionId = ((WebSocketBroadcaster)boardSessionBroadcaster).getBoardSessionIdBySessionId(session.getId());
        LeaveBoardInput input = leaveBoardUseCase.newInput();
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        input.setBoardId(UUID.fromString(session.getPathParameters().get("boardId")));
        input.setUserId(UUID.fromString(session.getPathParameters().get("userId")));
        input.setBoardSessionId(boardSessionId);

        leaveBoardUseCase.execute(input, presenter);

        ((WebSocketBroadcaster)boardSessionBroadcaster).removeSession(session.getId());
    }

    private void handleCursorMoved(JSONObject info) {
        Position newPosition = null;
        String userId = "";
        String boardId = "";
        try {
            userId = info.getString("userId");
            boardId = info.getString("boardId");
            double x = info.getJSONObject("position").getDouble("x");
            double y = info.getJSONObject("position").getDouble("y");
            newPosition = new Position(x, y);

        }catch (JSONException err){
            System.out.println(err);
            return;
        }
        MoveCursorInput input = moveCursorUseCase.newInput();
        CqrsCommandOutput output = CqrsCommandPresenter.newInstance();
        input.setUserId(UUID.fromString(userId));
        input.setBoardId(UUID.fromString(boardId));
        input.setPosition(newPosition);
        moveCursorUseCase.execute(input, output);
    }
}