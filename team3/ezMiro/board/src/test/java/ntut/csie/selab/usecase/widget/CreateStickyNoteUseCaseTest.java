package ntut.csie.selab.usecase.widget;

import ntut.csie.selab.adapter.board.BoardRepositoryImpl;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.WidgetRepositoryPeer;
import ntut.csie.selab.adapter.widget.WidgetRepositoryImpl;
import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.AbstractSpringBootJpaTest;
import ntut.csie.selab.usecase.JpaApplicationTest;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.eventHandler.NotifyBoard;
import ntut.csie.selab.usecase.eventHandler.NotifyUsersInBoard;
import ntut.csie.selab.usecase.websocket.WebSocket;
import ntut.csie.selab.usecase.widget.create.CreateStickyNoteUseCase;
import ntut.csie.selab.usecase.widget.create.CreateStickyNoteInput;
import ntut.csie.selab.usecase.widget.create.CreateStickyNoteOutput;
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

//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//@ExtendWith(SpringExtension.class)
//@TestPropertySource(locations = "classpath:test.properties")
//@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes= JpaApplicationTest.class)
@Rollback(false)
public class CreateStickyNoteUseCaseTest extends AbstractSpringBootJpaTest{

    @Autowired
    private WidgetRepositoryPeer widgetRepositoryPeer;

    @Test
    public void create_sticky_note_should_succeed() {
        // Arrange
        DomainEventBus domainEventBus = new DomainEventBus();
        WidgetRepository widgetRepository = new WidgetRepositoryImpl(widgetRepositoryPeer);
        CreateStickyNoteUseCase createStickyNoteUseCase = new CreateStickyNoteUseCase(widgetRepository, domainEventBus);
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
        Assert.assertTrue(widgetRepository.findById(output.getStickyNoteId()).isPresent());
        Assert.assertEquals(output.getStickyNoteId(), widgetRepository.findById(output.getStickyNoteId()).get().getId());
        Assert.assertEquals(1, domainEventBus.getCount());
    }

    @Test
    public void create_sticky_note_in_board_should_notify_board_successfully() {
        // Arrange
        BoardRepository boardRepository = new BoardRepositoryImpl();
        WidgetRepository widgetRepository = new WidgetRepositoryImpl(widgetRepositoryPeer);
        WebSocket webSocket = new FakeBoardWebSocket();
        String boardId = "1";
        boardRepository.add(createSimpleBoardWith(boardId));

        DomainEventBus domainEventBus = new DomainEventBus();
        NotifyBoard notifyBoard = new NotifyBoard(boardRepository, domainEventBus);

        NotifyUsersInBoard notifyUsersInBoard = new NotifyUsersInBoard(boardRepository, widgetRepository, domainEventBus, webSocket);
        domainEventBus.register(notifyBoard);
        domainEventBus.register(notifyUsersInBoard);
        CreateStickyNoteUseCase createStickyNoteUseCase = new CreateStickyNoteUseCase(widgetRepository, domainEventBus);
        CreateStickyNoteInput input = new CreateStickyNoteInput();
        CreateStickyNoteOutput output = new CreateStickyNoteOutput();
        input.setBoardId(boardId);
        input.setCoordinate(new Coordinate(1, 1, 2, 2));

        // Act
        createStickyNoteUseCase.execute(input, output);

        // Assert
        Assert.assertEquals(1, boardRepository.findById(boardId).get().getWidgetIds().size());
        Assert.assertEquals(3, domainEventBus.getCount());
    }

    private Board createSimpleBoardWith(String boardId) {
        String teamId = "1";
        String boardName = "first";
        return new Board(boardId, teamId, boardName);
    }
}

class FakeBoardWebSocket implements ntut.csie.selab.usecase.websocket.WebSocket {

    public void addSessionIn(String boardId, String userId, Session session) { }

    public void removeSessionFrom(String boardId, Session session) { }

    public void sendMessage(Session session, JSONObject message) { }

    public void sendMessageForAllUsersIn(String boardId, JSONObject message) { }
}
