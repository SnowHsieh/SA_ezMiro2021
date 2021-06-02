package ntut.csie.selab.usecase.widget.line;

import ntut.csie.selab.adapter.board.BoardRepositoryImpl;
import ntut.csie.selab.adapter.gateway.repository.springboot.board.BoardRepositoryPeer;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.LineRepositoryPeer;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.StickyNoteRepositoryPeer;
import ntut.csie.selab.adapter.widget.LineRepositoryImpl;
import ntut.csie.selab.adapter.widget.StickyNoteRepositoryImpl;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.Line;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.JpaApplicationTest;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.eventHandler.NotifyUsersInBoard;
import ntut.csie.selab.usecase.websocket.WebSocket;
import ntut.csie.selab.usecase.widget.LineRepository;
import ntut.csie.selab.usecase.widget.StickyNoteRepository;
import ntut.csie.selab.usecase.widget.line.move.MoveLineInput;
import ntut.csie.selab.usecase.widget.line.move.MoveLineOutput;
import ntut.csie.selab.usecase.widget.line.move.MoveLineUseCase;
import ntut.csie.selab.usecase.widget.stickynote.MoveStickyNoteUseCaseTest;
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
@ContextConfiguration(classes = JpaApplicationTest.class)
@Rollback(false)
public class MoveLineUseCaseTest {
    @Autowired
    private LineRepositoryPeer lineRepositoryPeer;

    @Autowired
    private BoardRepositoryPeer boardRepositoryPeer;

    @Autowired
    private StickyNoteRepositoryPeer stickyNoteRepositoryPeer;

    @Test
    public void move_line_should_succeed() {
        // Arrange
        BoardRepository boardRepository = new BoardRepositoryImpl(boardRepositoryPeer);
        LineRepository lineRepository = new LineRepositoryImpl(lineRepositoryPeer);
        StickyNoteRepository stickyNoteRepository = new StickyNoteRepositoryImpl(stickyNoteRepositoryPeer);
        DomainEventBus domainEventBus = new DomainEventBus();
        WebSocket webSocket = new FakeBoardWebSocket();
        NotifyUsersInBoard notifyUsersInBoard = new NotifyUsersInBoard(boardRepository, stickyNoteRepository, lineRepository, domainEventBus, webSocket);
        domainEventBus.register(notifyUsersInBoard);

        MoveLineUseCase moveLineUseCase = new MoveLineUseCase(lineRepository, domainEventBus);
        MoveLineInput input = new MoveLineInput();
        MoveLineOutput output = new MoveLineOutput();
        String boardId = "1";
        String lineId = "lineId";
        Coordinate lineCoordinate = new Coordinate(1, 1, 2, 2);
        Widget line = new Line(lineId, boardId, lineCoordinate);
        lineRepository.save(line);
        input.setLineId(lineId);
        input.setCoordinate(new Coordinate(100, 250, 200, 250));

        // Act
        moveLineUseCase.execute(input, output);

        // Assert
        Assert.assertEquals(new Point(100, 250), output.getCoordinate().getTopLeft());
        Assert.assertEquals(new Point(200, 250), output.getCoordinate().getBottomRight());
        Assert.assertEquals(2, domainEventBus.getCount());
    }

    class FakeBoardWebSocket implements ntut.csie.selab.usecase.websocket.WebSocket {

        public void addSessionIn(String boardId, String userId, Session session) { }

        public void removeSessionFrom(String boardId, Session session) { }

        public void sendMessage(Session session, JSONObject message) { }

        public void sendMessageForAllUsersIn(String boardId, JSONObject message) { }
    }
}
