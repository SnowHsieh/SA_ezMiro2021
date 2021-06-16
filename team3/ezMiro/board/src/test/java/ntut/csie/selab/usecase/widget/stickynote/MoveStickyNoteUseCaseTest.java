package ntut.csie.selab.usecase.widget.stickynote;

import ntut.csie.selab.adapter.board.BoardRepositoryImpl;
import ntut.csie.selab.adapter.gateway.repository.springboot.board.BoardRepositoryPeer;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.StickyNoteRepositoryPeer;
import ntut.csie.selab.adapter.widget.StickyNoteRepositoryImpl;
import ntut.csie.selab.entity.model.widget.Position;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.JpaApplicationTest;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.eventHandler.NotifyUsersInBoard;
import ntut.csie.selab.usecase.websocket.WebSocket;
import ntut.csie.selab.usecase.widget.StickyNoteRepository;
import ntut.csie.selab.usecase.widget.stickynote.move.MoveStickyNoteInput;
import ntut.csie.selab.usecase.widget.stickynote.move.MoveStickyNoteOutput;
import ntut.csie.selab.usecase.widget.stickynote.move.MoveStickyNoteUseCase;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.websocket.Session;
import java.awt.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes= JpaApplicationTest.class)
@Rollback(false)
public class MoveStickyNoteUseCaseTest {

    @Autowired
    private StickyNoteRepositoryPeer stickyNoteRepositoryPeer;

    @Autowired
    private BoardRepositoryPeer boardRepositoryPeer;

    @Test
    public void move_sticky_note_should_succeed() {
        // Arrange
        BoardRepository boardRepository = new BoardRepositoryImpl(boardRepositoryPeer);
        StickyNoteRepository stickyNoteRepository = new StickyNoteRepositoryImpl(stickyNoteRepositoryPeer);
        DomainEventBus domainEventBus = new DomainEventBus();
        WebSocket webSocket = new FakeBoardWebSocket();
        NotifyUsersInBoard notifyUsersInBoard = new NotifyUsersInBoard(boardRepository, stickyNoteRepository, domainEventBus, webSocket);
        domainEventBus.register(notifyUsersInBoard);

        String stickyNoteId = "1";
        Position stickyNotePosition = new Position(1, 1, 2, 2);
        Widget stickyNote = new StickyNote(stickyNoteId, "0", stickyNotePosition);
        stickyNoteRepository.save(stickyNote);
        MoveStickyNoteUseCase moveStickyNoteUseCase = new MoveStickyNoteUseCase(stickyNoteRepository, domainEventBus);
        MoveStickyNoteInput input = new MoveStickyNoteInput();
        MoveStickyNoteOutput output = new MoveStickyNoteOutput();
        input.setStickyNoteId(stickyNoteId);
        input.setPosition(new Position(4, 4, 5, 5));

        // Act
        moveStickyNoteUseCase.execute(input, output);

        // Assert
        Assert.assertEquals(new Point(4, 4), output.getPosition().getTopLeft());
        Assert.assertEquals(new Point(5, 5), output.getPosition().getBottomRight());
        Assert.assertEquals(1, domainEventBus.getCount());
    }

    class FakeBoardWebSocket implements ntut.csie.selab.usecase.websocket.WebSocket {

        public void addSessionIn(String boardId, String userId, Session session) { }

        public void removeSessionFrom(String boardId, Session session) { }

        public void sendMessage(Session session, JSONObject message) { }

        public void sendMessageForAllUsersIn(String boardId, JSONObject message) { }
    }
}
