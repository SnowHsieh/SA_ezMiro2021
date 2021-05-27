package ntut.csie.sslab.miro.adapter.controller.websocket;


import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.adapter.presenter.broadcastDomainEvent.DomainEventEncoder;
import ntut.csie.sslab.miro.application.springboot.web.config.websocket.EndpointConfigure;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.entity.model.board.event.cursor.CursorMoved;
import ntut.csie.sslab.miro.usecase.BoardSessionBroadcaster;
import ntut.csie.sslab.miro.usecase.board.enter.EnterBoardInput;
import ntut.csie.sslab.miro.usecase.board.enter.EnterBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.leave.LeaveBoardInput;
import ntut.csie.sslab.miro.usecase.board.leave.LeaveBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.moveCursor.MoveCursorInput;
import ntut.csie.sslab.miro.usecase.board.moveCursor.MoveCursorUseCase;
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

import static java.lang.String.format;

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
    public void onMessage(String message, Session session)  {
        String event = "";
        JSONObject info;
        try {
            JSONObject jsonObject = new JSONObject(message);
            jsonObject = jsonObject.getJSONObject("message");
            event = jsonObject.getString("event");
            info = jsonObject.getJSONObject("info");
            websocketEventHandler(event, info);
        }catch (JSONException err){
            System.out.println(err);
        }

    }

    private void websocketEventHandler(String event, JSONObject info) {
        if(CursorMoved.class.getSimpleName().equals(event)){
            handleCursorMoved(info);
        }
    }



    @OnOpen
    public void onOpen(Session session,
                       @PathParam("boardId") String boardId,
                       @PathParam("userId") String userId) throws IOException {
        EnterBoardInput input = enterBoardUseCase.newInput();
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        input.setUserId(userId);
        input.setBoardId(boardId);

        enterBoardUseCase.execute(input, presenter);

        ((WebSocketBroadcaster)boardSessionBroadcaster).addSession(presenter.getId(), session);
    }

    @OnClose
    public void onClose(Session session) {
        String boardSessionId = ((WebSocketBroadcaster)boardSessionBroadcaster).getBoardSessionIdBySessionId(session.getId());
        LeaveBoardInput input = leaveBoardUseCase.newInput();
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        input.setBoardId(session.getPathParameters().get("boardId"));
        input.setBoardSessionId(boardSessionId);

        leaveBoardUseCase.execute(input, presenter);
        System.out.println(format("Board Session: %s is disconnected.", presenter.getId()));

        ((WebSocketBroadcaster)boardSessionBroadcaster).removeSession(session.getId());
    }

    private void handleCursorMoved(JSONObject info) {
        Coordinate newPosition = null;
        String userId = "";
        String boardId = "";
        try {
            boardId = info.getString("boardId");
            Long x = info.getJSONObject("position").getLong("x");
            Long y = info.getJSONObject("position").getLong("y");
            newPosition = new Coordinate(x, y);
            userId = info.getString("userId");
        }catch (JSONException err){
            System.out.println(err);
            return;
        }
        MoveCursorInput input = moveCursorUseCase.newInput();
        CqrsCommandOutput output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setUserId(userId);
        input.setPosition(newPosition);
        moveCursorUseCase.execute(input, output);
    }
}
