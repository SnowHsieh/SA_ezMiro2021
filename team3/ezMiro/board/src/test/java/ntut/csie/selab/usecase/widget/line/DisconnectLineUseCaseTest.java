package ntut.csie.selab.usecase.widget.line;

import ntut.csie.selab.adapter.board.BoardRepositoryImpl;
import ntut.csie.selab.adapter.gateway.repository.springboot.board.BoardRepositoryPeer;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.LineRepositoryPeer;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.StickyNoteRepositoryPeer;
import ntut.csie.selab.adapter.widget.LineRepositoryImpl;
import ntut.csie.selab.adapter.widget.StickyNoteRepositoryImpl;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.Line;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.JpaApplicationTest;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.eventHandler.NotifyUsersInBoard;
import ntut.csie.selab.usecase.websocket.WebSocket;
import ntut.csie.selab.usecase.widget.LineRepository;
import ntut.csie.selab.usecase.widget.StickyNoteRepository;
import ntut.csie.selab.usecase.widget.line.disconnect.DisconnectLineInput;
import ntut.csie.selab.usecase.widget.line.disconnect.DisconnectLineOutput;
import ntut.csie.selab.usecase.widget.line.disconnect.DisconnectLineUseCase;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = JpaApplicationTest.class)
@Rollback(false)
public class DisconnectLineUseCaseTest {

    @Autowired
    private LineRepositoryPeer lineRepositoryPeer;

    @Autowired
    private StickyNoteRepositoryPeer stickyNoteRepositoryPeer;

    @Autowired
    private BoardRepositoryPeer boardRepositoryPeer;

    @Test
    public void disconnect_line_should_be_succeed() {
        // Arrange
        BoardRepository boardRepository = new BoardRepositoryImpl(boardRepositoryPeer);
        StickyNoteRepository stickyNoteRepository = new StickyNoteRepositoryImpl(stickyNoteRepositoryPeer);
        LineRepository lineRepository = new LineRepositoryImpl(lineRepositoryPeer);

        String boardId = "0";
        String lineId = "1";
        Coordinate lineCoordinate = new Coordinate(1, 1, 2, 2);
        Line line = new Line(lineId, boardId, lineCoordinate);

        lineRepository.save(line);
        String stickyNoteId = "2";
        Coordinate stickyNoteCoordinate = new Coordinate(1, 1, 2, 2);
        Widget stickyNote = new StickyNote(stickyNoteId, boardId, stickyNoteCoordinate);
        stickyNoteRepository.save(stickyNote);

        String endPoint = "head";
        line.link(endPoint, stickyNote.getId());
        lineRepository.save(line);
        WebSocket webSocket = new FakeBoardWebSocket();
        DomainEventBus domainEventBus = new DomainEventBus();
        NotifyUsersInBoard notifyUsersInBoard = new NotifyUsersInBoard(boardRepository, stickyNoteRepository, domainEventBus, webSocket);
        domainEventBus.register(notifyUsersInBoard);

        DisconnectLineUseCase disconnectLineUseCase = new DisconnectLineUseCase(lineRepository, domainEventBus);
        DisconnectLineInput input = new DisconnectLineInput();
        DisconnectLineOutput output = new DisconnectLineOutput();
        input.setBoardId(boardId);
        input.setLineId(lineId);
        input.setEndPoint(endPoint);

        // Act
        disconnectLineUseCase.execute(input, output);

        // Assert
        Assert.assertNull(((Line) lineRepository.findById(output.getLineId()).get()).getHeadWidgetId());
    }

    class FakeBoardWebSocket implements ntut.csie.selab.usecase.websocket.WebSocket {

        public void addSessionIn(String boardId, String userId, Session session) { }

        public void removeSessionFrom(String boardId, Session session) { }

        public void sendMessage(Session session, JSONObject message) { }

        public void sendMessageForAllUsersIn(String boardId, JSONObject message) { }
    }
}
