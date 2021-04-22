package ntut.csie.selab.usecase.widget;

import ntut.csie.selab.adapter.board.BoardRepositoryImpl;
import ntut.csie.selab.adapter.widget.WidgetRepositoryImpl;
import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.eventHandler.NotifyBoard;
import ntut.csie.selab.usecase.widget.create.CreateStickyNoteUseCase;
import ntut.csie.selab.usecase.widget.create.CreateStickyNoteInput;
import ntut.csie.selab.usecase.widget.create.CreateStickyNoteOutput;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

public class CreateStickyNoteUseCaseTest {

    @Test
    public void create_sticky_note_should_succeed() {
        // Arrange
        DomainEventBus domainEventBus = new DomainEventBus();
        WidgetRepository widgetRepository = new WidgetRepositoryImpl();
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
        String boardId = "1";
        boardRepository.add(createSimpleBoardWith(boardId));

        DomainEventBus domainEventBus = new DomainEventBus();
        NotifyBoard notifyBoard = new NotifyBoard(boardRepository, domainEventBus);
        domainEventBus.register(notifyBoard);
        WidgetRepositoryImpl widgetRepositoryImpl = new WidgetRepositoryImpl();
        CreateStickyNoteUseCase createStickyNoteUseCase = new CreateStickyNoteUseCase(widgetRepositoryImpl, domainEventBus);
        CreateStickyNoteInput input = new CreateStickyNoteInput();
        CreateStickyNoteOutput output = new CreateStickyNoteOutput();
        input.setBoardId(boardId);
        input.setCoordinate(new Coordinate(1, 1, 2, 2));

        // Act
        createStickyNoteUseCase.execute(input, output);

        // Assert
        Assert.assertEquals(1, boardRepository.findById(boardId).get().getWidgetIds().size());
        Assert.assertEquals(2, domainEventBus.getCount());
    }

    private Board createSimpleBoardWith(String boardId) {
        String teamId = "1";
        String boardName = "first";
        return new Board(boardId, teamId, boardName);
    }
}
