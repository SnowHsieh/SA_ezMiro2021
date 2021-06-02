package ntut.csie.selab.usecase.widget.stickynote;

import ntut.csie.selab.adapter.board.BoardRepositoryImpl;
import ntut.csie.selab.adapter.gateway.repository.springboot.board.BoardDataMapper;
import ntut.csie.selab.adapter.gateway.repository.springboot.board.BoardRepositoryPeer;
import ntut.csie.selab.adapter.gateway.repository.springboot.board.CommittedWidgetRepositoryPeer;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.StickyNoteRepositoryPeer;
import ntut.csie.selab.adapter.widget.StickyNoteRepositoryImpl;
import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.JpaApplicationTest;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.eventHandler.NotifyBoard;
import ntut.csie.selab.usecase.eventHandler.NotifyUsersInBoard;
import ntut.csie.selab.usecase.websocket.WebSocket;
import ntut.csie.selab.usecase.widget.StickyNoteRepository;
import ntut.csie.selab.usecase.widget.stickynote.create.CreateStickyNoteUseCase;
import ntut.csie.selab.usecase.widget.stickynote.create.CreateStickyNoteInput;
import ntut.csie.selab.usecase.widget.stickynote.create.CreateStickyNoteOutput;
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
public class CreateStickyNoteUseCaseTest {

    @Autowired
    private StickyNoteRepositoryPeer stickyNoteRepositoryPeer;

    @Autowired
    private BoardRepositoryPeer boardRepositoryPeer;

    @Autowired
    private CommittedWidgetRepositoryPeer committedWidgetRepositoryPeer;

    @Test
    public void create_sticky_note_should_succeed() {
        // Arrange
        DomainEventBus domainEventBus = new DomainEventBus();
        StickyNoteRepository stickyNoteRepository = new StickyNoteRepositoryImpl(stickyNoteRepositoryPeer);
        CreateStickyNoteUseCase createStickyNoteUseCase = new CreateStickyNoteUseCase(stickyNoteRepository, domainEventBus);
        CreateStickyNoteInput input = new CreateStickyNoteInput();
        CreateStickyNoteOutput output = new CreateStickyNoteOutput();
        input.setBoardId("1");
        input.setCoordinate(new Coordinate(1, 1, 2, 2));

        // Act
        createStickyNoteUseCase.execute(input, output);

        // Assert
        Assert.assertEquals("1", output.getBoardId());
        Assert.assertEquals(new Point(1, 1), output.getCoordinate().getTopLeft());
        Assert.assertEquals(new Point(2, 2), output.getCoordinate().getBottomRight());
        Assert.assertTrue(stickyNoteRepository.findById(output.getStickyNoteId()).isPresent());
        Assert.assertEquals(output.getStickyNoteId(), stickyNoteRepository.findById(output.getStickyNoteId()).get().getId());
        Assert.assertEquals(1, domainEventBus.getCount());
    }

    @Test
    public void create_sticky_note_in_board_should_notify_board_successfully() {
        // Arrange
        BoardRepository boardRepository = new BoardRepositoryImpl(boardRepositoryPeer);
        StickyNoteRepository stickyNoteRepository = new StickyNoteRepositoryImpl(stickyNoteRepositoryPeer);
        WebSocket webSocket = new FakeBoardWebSocket();
        String boardId = "1";
        boardRepository.save(createSimpleBoardWith(boardId));

        DomainEventBus domainEventBus = new DomainEventBus();
        NotifyBoard notifyBoard = new NotifyBoard(boardRepository, domainEventBus);

        NotifyUsersInBoard notifyUsersInBoard = new NotifyUsersInBoard(boardRepository, stickyNoteRepository, domainEventBus, webSocket);
        domainEventBus.register(notifyBoard);
        domainEventBus.register(notifyUsersInBoard);
        CreateStickyNoteUseCase createStickyNoteUseCase = new CreateStickyNoteUseCase(stickyNoteRepository, domainEventBus);
        CreateStickyNoteInput input = new CreateStickyNoteInput();
        CreateStickyNoteOutput output = new CreateStickyNoteOutput();
        input.setBoardId(boardId);
        input.setCoordinate(new Coordinate(1, 1, 2, 2));

        // Act
        createStickyNoteUseCase.execute(input, output);

        // Assert
        Assert.assertEquals(1, committedWidgetRepositoryPeer.countByBoard(BoardDataMapper.domainToData(boardRepository.findById(boardId).get())));
        Assert.assertEquals(3, domainEventBus.getCount());
    }

    private Board createSimpleBoardWith(String boardId) {
        String teamId = "1";
        String boardName = "first";
        return new Board(boardId, teamId, boardName);
    }

    class FakeBoardWebSocket implements ntut.csie.selab.usecase.websocket.WebSocket {

        public void addSessionIn(String boardId, String userId, Session session) { }

        public void removeSessionFrom(String boardId, Session session) { }

        public void sendMessage(Session session, JSONObject message) { }

        public void sendMessageForAllUsersIn(String boardId, JSONObject message) { }
    }
}


