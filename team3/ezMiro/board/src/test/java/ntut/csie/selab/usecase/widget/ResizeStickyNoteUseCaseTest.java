package ntut.csie.selab.usecase.widget;

import ntut.csie.selab.adapter.board.BoardAssociationRepositoryImpl;
import ntut.csie.selab.adapter.board.BoardRepositoryImpl;
import ntut.csie.selab.adapter.gateway.repository.springboot.board.BoardRepositoryPeer;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.WidgetRepositoryPeer;
import ntut.csie.selab.adapter.widget.WidgetRepositoryImpl;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.JpaApplicationTest;
import ntut.csie.selab.usecase.board.BoardAssociationRepository;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.eventHandler.NotifyBoard;
import ntut.csie.selab.usecase.eventHandler.NotifyUsersInBoard;
import ntut.csie.selab.usecase.websocket.WebSocket;
import ntut.csie.selab.usecase.widget.resize.ResizeStickyNoteUseCase;
import ntut.csie.selab.usecase.widget.resize.ResizeStickyNoteInput;
import ntut.csie.selab.usecase.widget.resize.ResizeStickyNoteOutput;
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
    private WidgetRepositoryPeer widgetRepositoryPeer;

    @Autowired
    private BoardRepositoryPeer boardRepositoryPeer;

    @Test
    public void resize_sticky_note_should_succed() {
        // Arrange
        BoardRepository boardRepository = new BoardRepositoryImpl(boardRepositoryPeer);
        WidgetRepository widgetRepository = new WidgetRepositoryImpl(widgetRepositoryPeer);
        DomainEventBus domainEventBus = new DomainEventBus();
        WebSocket webSocket = new FakeBoardWebSocket();
        NotifyUsersInBoard notifyUsersInBoard = new NotifyUsersInBoard(boardRepository, widgetRepository, domainEventBus, webSocket);
        domainEventBus.register(notifyUsersInBoard);

        String stickyNoteId = "77946-45641-7546";
        Coordinate coordinate = new Coordinate(10, 10, 100, 100);
        Widget stickyNote = new StickyNote(stickyNoteId, "0", coordinate);
        widgetRepository.save(stickyNote);
        ResizeStickyNoteUseCase resizeStickyNoteUseCase = new ResizeStickyNoteUseCase(widgetRepository, domainEventBus);
        ResizeStickyNoteInput input = new ResizeStickyNoteInput();
        ResizeStickyNoteOutput output = new ResizeStickyNoteOutput();
        input.setStickyNoteId(stickyNoteId);
        input.setCoordinate(new Coordinate(10, 10, 50, 50));

        // Act
        resizeStickyNoteUseCase.execute(input, output);

        // Assert
        Assert.assertEquals(new Point(10, 10), output.getCoordinate().getTopLeft());
        Assert.assertEquals(new Point(50, 50), output.getCoordinate().getBottomRight());
        Assert.assertEquals(2, domainEventBus.getCount());

    }

    class FakeBoardWebSocket implements ntut.csie.selab.usecase.websocket.WebSocket {

        public void addSessionIn(String boardId, String userId, Session session) { }

        public void removeSessionFrom(String boardId, Session session) { }

        public void sendMessage(Session session, JSONObject message) { }

        public void sendMessageForAllUsersIn(String boardId, JSONObject message) { }
    }
}
