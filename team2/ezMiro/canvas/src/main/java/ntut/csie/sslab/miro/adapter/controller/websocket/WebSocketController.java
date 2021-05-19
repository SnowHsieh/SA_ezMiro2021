package ntut.csie.sslab.miro.adapter.controller.websocket;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.usecase.board.enter.EnterBoardInput;
import ntut.csie.sslab.miro.usecase.board.enter.EnterBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.leave.LeaveBoardInput;
import ntut.csie.sslab.miro.usecase.board.leave.LeaveBoardUseCase;
import ntut.csie.sslab.miro.usecase.note.NoteDTO;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WebSocketController {

    private Map<String, OnlineUserDTO> allOnlineUsers = new HashMap<>();
    private Map<String, String> sessionBoardMap = new HashMap<>();
    private List<NoteDTO> allOnlineNotes = new ArrayList<>();
    private EnterBoardUseCase enterBoardUseCase;
    private LeaveBoardUseCase leaveBoardUseCase;
    private List<String> targetSession = new ArrayList<>();

    @Autowired
    public void setEnterBoardUseCase(EnterBoardUseCase enterBoardUseCase) {
        this.enterBoardUseCase = enterBoardUseCase;
    }

    @Autowired
    public void setLeaveBoardUseCase(LeaveBoardUseCase leaveBoardUseCase) {
        this.leaveBoardUseCase = leaveBoardUseCase;
    }

    @Autowired
    SimpMessagingTemplate template;

    @MessageMapping("/sendMessage/{boardChannel}")
    public void receiveMessage(@Payload OnlineUserDTO onlineUserDTO, @Header("simpSessionId") String sessionId , @DestinationVariable String boardChannel) {
        allOnlineUsers.put(sessionId, onlineUserDTO);
        template.convertAndSend("/topic/"+boardChannel+"/position", allOnlineUsers.values());
    }

    @MessageMapping("/sendNote")
    public void receiveNote(@Payload List<NoteDTO> noteDtos, @Header("simpSessionId") String sessionId) {
        allOnlineNotes = noteDtos;
        template.convertAndSend("/topic/note", allOnlineNotes);
    }

    @MessageMapping("/enterBoard/{boardId}")
    public void enterBoard(@Payload String userId, @DestinationVariable String boardId, @Header("simpSessionId") String sessionId){
        targetSession.add(sessionId);
        OnlineUserDTO onlineUserDTO = new OnlineUserDTO();
        onlineUserDTO.setUserId(userId);
        allOnlineUsers.put(sessionId, onlineUserDTO);
        EnterBoardInput input = enterBoardUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setUserId(userId);

        enterBoardUseCase.execute(input, output);
        sessionBoardMap.put(sessionId, boardId);
    }

    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        if(targetSession.contains(event.getSessionId())){
            LeaveBoardInput input = leaveBoardUseCase.newInput();
            CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
            input.setBoardId(sessionBoardMap.get(event.getSessionId()));
            input.setUserId(allOnlineUsers.get(event.getSessionId()).getUserId());

            leaveBoardUseCase.execute(input, output);
            sessionBoardMap.remove(event.getSessionId());
            allOnlineUsers.remove(event.getSessionId());
        }
    }
}