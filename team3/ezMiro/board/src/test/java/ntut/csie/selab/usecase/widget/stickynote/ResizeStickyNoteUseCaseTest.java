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
import ntut.csie.selab.usecase.widget.stickynote.resize.ResizeStickyNoteUseCase;
import ntut.csie.selab.usecase.widget.stickynote.resize.ResizeStickyNoteInput;
import ntut.csie.selab.usecase.widget.stickynote.resize.ResizeStickyNoteOutput;
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
public class ResizeStickyNoteUseCaseTest {

    @Autowired
    private StickyNoteRepositoryPeer stickyNoteRepositoryPeer;

    @Autowired
    private BoardRepositoryPeer boardRepositoryPeer;

    @Test
    public void resize_sticky_note_should_succed() {
        // Arrange
        BoardRepository boardRepository = new BoardRepositoryImpl(boardRepositoryPeer);
        StickyNoteRepository stickyNoteRepository = new StickyNoteRepositoryImpl(stickyNoteRepositoryPeer);
        DomainEventBus domainEventBus = new DomainEventBus();
        WebSocket webSocket = new FakeBoardWebSocket();
        NotifyUsersInBoard notifyUsersInBoard = new NotifyUsersInBoard(boardRepository, stickyNoteRepository, domainEventBus, webSocket);
        domainEventBus.register(notifyUsersInBoard);

        String stickyNoteId = "77946-45641-7546";
        Position position = new Position(10, 10, 100, 100);
        Widget stickyNote = new StickyNote(stickyNoteId, "0", position);
        stickyNoteRepository.save(stickyNote);
        ResizeStickyNoteUseCase resizeStickyNoteUseCase = new ResizeStickyNoteUseCase(stickyNoteRepository, domainEventBus);
        ResizeStickyNoteInput input = new ResizeStickyNoteInput();
        ResizeStickyNoteOutput output = new ResizeStickyNoteOutput();
        input.setStickyNoteId(stickyNoteId);
        input.setPosition(new Position(10, 10, 50, 50));

        // Act
        resizeStickyNoteUseCase.execute(input, output);

        // Assert
        Assert.assertEquals(new Point(10, 10), output.getPosition().getTopLeft());
        Assert.assertEquals(new Point(50, 50), output.getPosition().getBottomRight());
        Assert.assertEquals(1, domainEventBus.getCount());

    }

    class FakeBoardWebSocket implements ntut.csie.selab.usecase.websocket.WebSocket {

        public void addSessionIn(String boardId, String userId, Session session) { }

        public void removeSessionFrom(String boardId, Session session) { }

        public void sendMessage(Session session, JSONObject message) { }

        public void sendMessageForAllUsersIn(String boardId, JSONObject message) { }
    }
}
