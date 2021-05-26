package ntut.csie.sslab.miro.adapter.controller.websocket;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;
import ntut.csie.sslab.miro.usecase.board.enter.EnterBoardInput;
import ntut.csie.sslab.miro.usecase.board.enter.EnterBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.leave.LeaveBoardInput;
import ntut.csie.sslab.miro.usecase.board.leave.LeaveBoardUseCase;
import ntut.csie.sslab.miro.usecase.cursor.CursorRepository;
import ntut.csie.sslab.miro.usecase.cursor.move.MoveCursorInput;
import ntut.csie.sslab.miro.usecase.cursor.move.MoveCursorUseCase;
import ntut.csie.sslab.miro.usecase.note.OnlineUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WebSocketController {
    private Map<String, String> allOnlineUsers = new HashMap<>();
    private Map<String, String> sessionBoardMap = new HashMap<>();
    private EnterBoardUseCase enterBoardUseCase;
    private LeaveBoardUseCase leaveBoardUseCase;
    private MoveCursorUseCase moveCursorUseCase;
    private CursorRepository cursorRepository;

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
    public void setCursorRepository(CursorRepository cursorRepository) {
        this.cursorRepository = cursorRepository;
    }

    @Autowired
    SimpMessagingTemplate template;

    @MessageMapping("/sendMessage/{boardChannel}")
    public void receiveMessage(@Payload OnlineUserDTO onlineUserDTO, @Header("simpSessionId") String sessionId , @DestinationVariable String boardChannel) {
        allOnlineUsers.put(sessionId, onlineUserDTO.getUserId());
        MoveCursorInput input = moveCursorUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setCursorId(cursorRepository.findByUserId(onlineUserDTO.getUserId()).get().getId());
        input.setCoordinate(new Coordinate(onlineUserDTO.getX(), onlineUserDTO.getY()));

        moveCursorUseCase.execute(input, output);
    }

    @MessageMapping("/sendNote/{boardChannel}")
    public void receiveNote(@Payload String noteCoordinate, @DestinationVariable String boardChannel) {
        template.convertAndSend("/topic/" + boardChannel + "/draggingNote", noteCoordinate);
    }

    @MessageMapping("/enterBoard/{boardId}")
    public void enterBoard(@Payload String userId, @DestinationVariable String boardId, @Header("simpSessionId") String sessionId){
        EnterBoardInput input = enterBoardUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setUserId(userId);

        enterBoardUseCase.execute(input, output);
        allOnlineUsers.put(sessionId, userId);
        sessionBoardMap.put(sessionId, boardId);
    }

    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        if(allOnlineUsers.containsKey(event.getSessionId())){
            LeaveBoardInput input = leaveBoardUseCase.newInput();
            CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
            input.setBoardId(sessionBoardMap.get(event.getSessionId()));
            input.setUserId(allOnlineUsers.get(event.getSessionId()));

            leaveBoardUseCase.execute(input, output);
            sessionBoardMap.remove(event.getSessionId());
            allOnlineUsers.remove(event.getSessionId());
        }
    }
}