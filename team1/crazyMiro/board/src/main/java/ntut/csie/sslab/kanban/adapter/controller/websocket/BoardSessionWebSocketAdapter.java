package ntut.csie.sslab.kanban.adapter.controller.websocket;


import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.kanban.adapter.presenter.broadcastDomainEvent.DomainEventEncoder;
import ntut.csie.sslab.kanban.application.springboot.web.config.websocket.EndpointConfigure;
import ntut.csie.sslab.kanban.usecase.BoardSessionBroadcaster;
import ntut.csie.sslab.kanban.usecase.cursor.create.CreateCursorInput;
import ntut.csie.sslab.kanban.usecase.cursor.create.CreateCursorUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/websocket/{boardId}/{ip}",
        encoders = {DomainEventEncoder.class},
        configurator = EndpointConfigure.class)
@Component
public class BoardSessionWebSocketAdapter {


    private CreateCursorUseCase createCursorUseCase;
    private BoardSessionBroadcaster boardSessionBroadcaster;

    @Autowired
    public void setCreateCursorUseCase(CreateCursorUseCase createCursorUseCase) {
        this.createCursorUseCase = createCursorUseCase;
    }


    @Autowired
    public void setBoardSessionBroadcaster(BoardSessionBroadcaster boardSessionBroadcaster) {
        this.boardSessionBroadcaster = boardSessionBroadcaster;
    }

    @OnMessage
    public void onMessage(String message, Session session)  {
    }


    @OnOpen
    public void onOpen(Session session,
                       @PathParam("boardId") String boardId,
                       @PathParam("ip") String ip) throws IOException {
        CreateCursorInput input = createCursorUseCase.newInput();
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setIp(ip);
        createCursorUseCase.execute(input, presenter);

        ((WebSocketBroadcaster)boardSessionBroadcaster).addSession(presenter.getId(), session);
    }

    @OnClose
    public void onClose(Session session) {
//        String boardSessionId = ((WebSocketBroadcaster)boardSessionBroadcaster).getBoardSessionIdBySessionId(session.getId());
//        String boardId = session.getPathParameters().get("boardId");
//
//        LeaveBoardInput input = leaveBoardUseCase.newInput();
//        input.setBoardSessionId(boardSessionId);
//        input.setBoardId(boardId);
//
//        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
//        leaveBoardUseCase.execute(input, presenter);
//        ((WebSocketBroadcaster)boardSessionBroadcaster).removeSession(presenter.getId());
    }
}
