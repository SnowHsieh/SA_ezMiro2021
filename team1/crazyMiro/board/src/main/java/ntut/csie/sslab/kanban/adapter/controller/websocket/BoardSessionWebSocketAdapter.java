package ntut.csie.sslab.kanban.adapter.controller.websocket;


import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.kanban.adapter.presenter.broadcastDomainEvent.DomainEventEncoder;
import ntut.csie.sslab.kanban.application.springboot.web.config.websocket.EndpointConfigure;
import ntut.csie.sslab.kanban.entity.model.Coordinate;
import ntut.csie.sslab.kanban.entity.model.cursor.event.CursorMoved;
import ntut.csie.sslab.kanban.usecase.BoardSessionBroadcaster;
import ntut.csie.sslab.kanban.usecase.board.enter.EnterBoardInput;
import ntut.csie.sslab.kanban.usecase.board.enter.EnterBoardUseCase;
import ntut.csie.sslab.kanban.usecase.board.leave.LeaveBoardInput;
import ntut.csie.sslab.kanban.usecase.board.leave.LeaveBoardUseCase;
import ntut.csie.sslab.kanban.usecase.board.leave.LeaveBoardUseCaseImpl;
import ntut.csie.sslab.kanban.usecase.cursor.move.MoveCursorInput;
import ntut.csie.sslab.kanban.usecase.cursor.move.MoveCursorUseCase;
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

        ((WebSocketBroadcaster)boardSessionBroadcaster).removeSession(session.getId());
    }

    private void handleCursorMoved(JSONObject info) {
        Coordinate newPosition = null;
        String cursorId = "";
        try {
            Long x = info.getJSONObject("position").getLong("x");
            Long y = info.getJSONObject("position").getLong("y");
            newPosition = new Coordinate(x, y);
            cursorId = info.getString("cursorId");
        }catch (JSONException err){
            System.out.println(err);
            return;
        }
        MoveCursorInput input = moveCursorUseCase.newInput();
        CqrsCommandOutput output = CqrsCommandPresenter.newInstance();
        input.setCursorId(cursorId);
        input.setPosition(newPosition);
        moveCursorUseCase.execute(input, output);
    }
}
