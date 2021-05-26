package ntut.csie.selab.usecase.widget;

import ntut.csie.selab.adapter.board.BoardRepositoryInMemoryImpl;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.WidgetRepositoryPeer;
import ntut.csie.selab.adapter.widget.WidgetRepositoryImpl;
import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.board.CommittedWidget;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.JpaApplicationTest;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.eventHandler.NotifyBoard;
import ntut.csie.selab.usecase.eventHandler.NotifyUsersInBoard;
import ntut.csie.selab.usecase.websocket.WebSocket;
import ntut.csie.selab.usecase.widget.delete.DeleteStickyNoteInput;
import ntut.csie.selab.usecase.widget.delete.DeleteStickyNoteOutput;
import ntut.csie.selab.usecase.widget.delete.DeleteStickyNoteUseCase;
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
    private WidgetRepositoryPeer widgetRepositoryPeer;

    @Test
    public void delete_sticky_note_should_successd() {
        // Arrange
        WidgetRepository widgetRepository = new WidgetRepositoryImpl(widgetRepositoryPeer);
        String stickyNoteId = "1";
        Coordinate stickyNoteCoordinate = new Coordinate(1, 1, 2, 2);
        Widget stickyNote = new StickyNote(stickyNoteId, "0", stickyNoteCoordinate);
        widgetRepository.save(stickyNote);

        DomainEventBus domainEventBus = new DomainEventBus();
        DeleteStickyNoteUseCase deleteStickyNoteUseCase = new DeleteStickyNoteUseCase(widgetRepository, domainEventBus);
        DeleteStickyNoteInput input = new DeleteStickyNoteInput();
        DeleteStickyNoteOutput output = new DeleteStickyNoteOutput();
        input.setStickyNoteId("1");

        // Act
        deleteStickyNoteUseCase.execute(input, output);

        // Assert
        Assert.assertNotNull(output.getStickyNoteId());
        Assert.assertFalse(widgetRepository.findById(output.getStickyNoteId()).isPresent());
    }

    @Test
    public void delete_sticky_note_in_board_should_notify_board_successfully() {
        // Arrange
        BoardRepository boardRepository = new BoardRepositoryInMemoryImpl();
        String boardId = "boardId";
        String stickyNoteId = "stickyNoteId";

        boardRepository.save(createBoardHasStickNoteWith(boardId, stickyNoteId));

        WidgetRepository widgetRepository = new WidgetRepositoryImpl(widgetRepositoryPeer);
        WebSocket webSocket = new FakeBoardWebSocket();
        Coordinate stickyNoteCoordinate = new Coordinate(1, 1, 2, 2);
        Widget stickyNote = new StickyNote(stickyNoteId, "boardId", stickyNoteCoordinate);
        widgetRepository.save(stickyNote);
        stickyNote.clearDomainEvents();

        DomainEventBus domainEventBus = new DomainEventBus();
        NotifyBoard notifyBoard = new NotifyBoard(boardRepository, domainEventBus);
        NotifyUsersInBoard notifyUsersInBoard = new NotifyUsersInBoard(boardRepository, widgetRepository, domainEventBus, webSocket);
        domainEventBus.register(notifyBoard);
        domainEventBus.register(notifyUsersInBoard);
        DeleteStickyNoteUseCase deleteStickyNoteUseCase = new DeleteStickyNoteUseCase(widgetRepository, domainEventBus);
        DeleteStickyNoteInput input = new DeleteStickyNoteInput();
        DeleteStickyNoteOutput output = new DeleteStickyNoteOutput();
        input.setStickyNoteId("stickyNoteId");

        // Act
        deleteStickyNoteUseCase.execute(input, output);

        // Assert
        Assert.assertNotNull(output.getStickyNoteId());
        Assert.assertFalse(widgetRepository.findById(output.getStickyNoteId()).isPresent());
        Assert.assertEquals(0, boardRepository.findById(boardId).get().getWidgetIds().size());
        Assert.assertEquals(3, domainEventBus.getCount());
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

