package ntut.csie.team5.adapter.controller.websocket;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.team5.application.springboot.web.config.websocket.DomainEventEncoder;
import ntut.csie.team5.application.springboot.web.config.websocket.EndPointConfiguration;
import ntut.csie.team5.usecase.board.BoardSessionBroadcaster;
import ntut.csie.team5.usecase.board.enter.EnterBoardInput;
import ntut.csie.team5.usecase.board.enter.EnterBoardUseCase;
import ntut.csie.team5.usecase.board.leave.LeaveBoardInput;
import ntut.csie.team5.usecase.board.leave.LeaveBoardUseCase;
import ntut.csie.team5.usecase.board.move_cursor.MoveCursorInput;
import ntut.csie.team5.usecase.board.move_cursor.MoveCursorUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Component
@ServerEndpoint(value = "/WebSocketServer/{boardId}/{userId}",
                encoders = {DomainEventEncoder.class},
                configurator = EndPointConfiguration.class)
public class WebSocketController {

    private EnterBoardUseCase enterBoardUseCase;
    private LeaveBoardUseCase leaveBoardUseCase;
    private MoveCursorUseCase moveCursorUseCase;
    private BoardSessionBroadcaster boardSessionBroadcaster;

    @Autowired
    public void setEnterBoardUseCase(EnterBoardUseCase enterBoardUseCase) {
        this.enterBoardUseCase = enterBoardUseCase;
    }
    @Autowired
    public void setLeaveBoardUseCase(LeaveBoardUseCase leaveBoardUseCase) {
        this.leaveBoardUseCase = leaveBoardUseCase;
    }

    @Autowired
    public void setMoveCursorUseCase(MoveCursorUseCase moveCursorUseCase) {
        this.moveCursorUseCase = moveCursorUseCase;
    }

    @Autowired
    public void setBoardSessionBroadcaster(BoardSessionBroadcaster boardSessionBroadcaster) {
        this.boardSessionBroadcaster = boardSessionBroadcaster;
    }

    @OnOpen
    public void onOpen(@PathParam(value = "boardId") String boardId, @PathParam(value = "userId") String userId, Session session) {
        EnterBoardInput input = enterBoardUseCase.newInput();
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        input.setBoardId(boardId);
        input.setUserId(userId);

        enterBoardUseCase.execute(input, presenter);

        session.setMaxIdleTimeout(30 * 60);
        ((WebSocketBroadcaster) boardSessionBroadcaster).addSession(presenter.getId(), session);
    }

    @OnClose
    public void onClose(@PathParam(value = "boardId") String boardId, @PathParam(value = "userId") String userId, Session session) {
        LeaveBoardInput input = leaveBoardUseCase.newInput();
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        String boardSessionId = ((WebSocketBroadcaster) boardSessionBroadcaster).getBoardSessionIdBySessionId(session.getId());

        input.setBoardId(boardId);
        input.setUserId(userId);
        input.setBoardSessionId(boardSessionId);

        leaveBoardUseCase.execute(input, presenter);

        ((WebSocketBroadcaster) boardSessionBroadcaster).removeSession(presenter.getId());
    }

    @OnMessage
    public void onMessage(@PathParam(value = "boardId") String boardId, @PathParam(value = "userId") String userId, String message, Session session) {
        String messageType = "";
        try {
            JSONObject messageTypeJSON = new JSONObject(message);
            if (messageTypeJSON.has("type")) {
                messageType = messageTypeJSON.getString("type");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (messageType.equals("MOVE_CURSOR")) {
            MoveCursorInput input = moveCursorUseCase.newInput();
            CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
            String boardSessionId = ((WebSocketBroadcaster) boardSessionBroadcaster).getBoardSessionIdBySessionId(session.getId());

            input.setBoardId(boardId);
            input.setBoardSessionId(boardSessionId);

            try {
                JSONObject pointer = new JSONObject(message);
                input.setPositionX(pointer.getInt("x"));
                input.setPositionY(pointer.getInt("y"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            moveCursorUseCase.execute(input, presenter);
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
