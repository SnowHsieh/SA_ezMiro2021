package ntut.csie.selab.usecase.widget.stickynote;

import ntut.csie.selab.adapter.board.BoardRepositoryImpl;
import ntut.csie.selab.adapter.gateway.repository.springboot.board.BoardRepositoryPeer;
import ntut.csie.selab.adapter.gateway.repository.springboot.board.CommittedWidgetRepositoryPeer;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.LineRepositoryPeer;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.StickyNoteRepositoryPeer;
import ntut.csie.selab.adapter.widget.LineRepositoryImpl;
import ntut.csie.selab.adapter.widget.StickyNoteRepositoryImpl;
import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.board.CommittedWidget;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.Line;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.JpaApplicationTest;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.eventHandler.NotifyBoard;
import ntut.csie.selab.usecase.eventHandler.NotifyLine;
import ntut.csie.selab.usecase.eventHandler.NotifyUsersInBoard;
import ntut.csie.selab.usecase.websocket.WebSocket;
import ntut.csie.selab.usecase.widget.LineRepository;
import ntut.csie.selab.usecase.widget.StickyNoteRepository;
import ntut.csie.selab.usecase.widget.line.MoveLineUseCaseTest;
import ntut.csie.selab.usecase.widget.stickynote.delete.DeleteStickyNoteInput;
import ntut.csie.selab.usecase.widget.stickynote.delete.DeleteStickyNoteOutput;
import ntut.csie.selab.usecase.widget.stickynote.delete.DeleteStickyNoteUseCase;
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
@ContextConfiguration(classes= JpaApplicationTest.class)
@Rollback(false)
public class DeleteStickyNoteUseCaseTest {

    @Autowired
    private StickyNoteRepositoryPeer stickyNoteRepositoryPeer;

    @Autowired
    private LineRepositoryPeer lineRepositoryPeer;

    @Autowired
    private BoardRepositoryPeer boardRepositoryPeer;

    @Autowired
    private CommittedWidgetRepositoryPeer committedWidgetRepositoryPeer;

    @Test
    public void delete_sticky_note_should_successd() {
        // Arrange
        StickyNoteRepository stickyNoteRepository = new StickyNoteRepositoryImpl(stickyNoteRepositoryPeer);
        String stickyNoteId = "1";
        Coordinate stickyNoteCoordinate = new Coordinate(1, 1, 2, 2);
        Widget stickyNote = new StickyNote(stickyNoteId, "0", stickyNoteCoordinate);
        stickyNoteRepository.save(stickyNote);

        DomainEventBus domainEventBus = new DomainEventBus();
        DeleteStickyNoteUseCase deleteStickyNoteUseCase = new DeleteStickyNoteUseCase(stickyNoteRepository, domainEventBus);
        DeleteStickyNoteInput input = new DeleteStickyNoteInput();
        DeleteStickyNoteOutput output = new DeleteStickyNoteOutput();
        input.setStickyNoteId("1");

        // Act
        deleteStickyNoteUseCase.execute(input, output);

        // Assert
        Assert.assertNotNull(output.getStickyNoteId());
        Assert.assertFalse(stickyNoteRepository.findById(output.getStickyNoteId()).isPresent());
    }

    @Test
    public void delete_sticky_note_in_board_should_notify_board_successfully() {
        // Arrange
        BoardRepository boardRepository = new BoardRepositoryImpl(boardRepositoryPeer);
        String boardId = "deletedBoardId";
        String stickyNoteId = "deletedStickyNoteId";

        boardRepository.save(createBoardHasStickNoteWith(boardId, stickyNoteId));

        StickyNoteRepository stickyNoteRepository = new StickyNoteRepositoryImpl(stickyNoteRepositoryPeer);
        WebSocket webSocket = new FakeBoardWebSocket();
        Coordinate stickyNoteCoordinate = new Coordinate(1, 1, 2, 2);
        Widget stickyNote = new StickyNote(stickyNoteId, boardId, stickyNoteCoordinate);
        stickyNoteRepository.save(stickyNote);
        stickyNote.clearDomainEvents();

        DomainEventBus domainEventBus = new DomainEventBus();
        NotifyBoard notifyBoard = new NotifyBoard(boardRepository, domainEventBus);
        NotifyUsersInBoard notifyUsersInBoard = new NotifyUsersInBoard(boardRepository, stickyNoteRepository, domainEventBus, webSocket);
        domainEventBus.register(notifyBoard);
        domainEventBus.register(notifyUsersInBoard);
        DeleteStickyNoteUseCase deleteStickyNoteUseCase = new DeleteStickyNoteUseCase(stickyNoteRepository, domainEventBus);
        DeleteStickyNoteInput input = new DeleteStickyNoteInput();
        DeleteStickyNoteOutput output = new DeleteStickyNoteOutput();
        input.setStickyNoteId(stickyNoteId);

        // Act
        deleteStickyNoteUseCase.execute(input, output);

        // Assert
        Assert.assertNotNull(output.getStickyNoteId());
        Assert.assertFalse(stickyNoteRepository.findById(output.getStickyNoteId()).isPresent());
        Assert.assertEquals(0, boardRepository.findById(boardId).get().getWidgetIds().size());
        Assert.assertEquals(3, domainEventBus.getCount());
    }

    @Test
    public void delete_sticky_note_which_has_a_linking_line_should_successd() {
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

        line.link("head", stickyNote.getId());
        lineRepository.save(line);

        WebSocket webSocket = new FakeBoardWebSocket();
        DomainEventBus domainEventBus = new DomainEventBus();
        NotifyUsersInBoard notifyUsersInBoard = new NotifyUsersInBoard(boardRepository, stickyNoteRepository, domainEventBus, webSocket);
        NotifyLine notifyLine = new NotifyLine(boardRepository, lineRepository, domainEventBus);
        domainEventBus.register(notifyUsersInBoard);
        domainEventBus.register(notifyLine);

        DeleteStickyNoteUseCase deleteStickyNoteUseCase = new DeleteStickyNoteUseCase(stickyNoteRepository, domainEventBus);
        DeleteStickyNoteInput input = new DeleteStickyNoteInput();
        DeleteStickyNoteOutput output = new DeleteStickyNoteOutput();
        input.setStickyNoteId(stickyNoteId);

        // Act
        deleteStickyNoteUseCase.execute(input, output);

        // Assert
        Assert.assertNotNull(output.getStickyNoteId());
        Assert.assertFalse(stickyNoteRepository.findById(output.getStickyNoteId()).isPresent());
        Assert.assertNull(((Line) lineRepository.findById(line.getId()).get()).getHeadWidgetId());
    }

    private Board createBoardHasStickNoteWith(String boardId, String stickyNoteId) {
        String teamId = "1";
        String boardName = "first";
        Board board = new Board(boardId, teamId, boardName);
        board.getCommittedWidgets().add(new CommittedWidget(boardId, stickyNoteId, board.getCommittedWidgets().size()));
        return board;
    }

    class FakeBoardWebSocket implements ntut.csie.selab.usecase.websocket.WebSocket {

        public void addSessionIn(String boardId, String userId, Session session) { }

        public void removeSessionFrom(String boardId, Session session) { }

        public void sendMessage(Session session, JSONObject message) { }

        public void sendMessageForAllUsersIn(String boardId, JSONObject message) { }
    }
}

