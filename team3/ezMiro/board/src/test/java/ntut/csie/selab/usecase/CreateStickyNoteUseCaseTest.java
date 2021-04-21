package ntut.csie.selab.usecase;

import ntut.csie.selab.adapter.board.BoardRepositoryImpl;
import ntut.csie.selab.adapter.widget.WidgetRepositoryImpl;
import ntut.csie.selab.entity.model.Board;
import ntut.csie.selab.entity.model.Coordinate;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.eventHandler.NotifyBoard;
import ntut.csie.selab.usecase.widget.CreateStickyNoteUseCase;
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
        WidgetRepositoryImpl widgetRepositoryImpl = new WidgetRepositoryImpl();
        CreateStickyNoteUseCase createStickyNoteUseCase = new CreateStickyNoteUseCase(widgetRepositoryImpl, domainEventBus);
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
        Assert.assertTrue(widgetRepositoryImpl.findById(output.getStickyNoteId()).isPresent());
        Assert.assertEquals(output.getStickyNoteId(), widgetRepositoryImpl.findById(output.getStickyNoteId()).get().getId());
        Assert.assertEquals(1, domainEventBus.getCount());
    }

    @Test
    public void create_sticky_note_in_board_should_notify_board_successfully() {
        // Arrange
        BoardRepositoryImpl boardRepositoryImpl = new BoardRepositoryImpl();
        String boardId = "1";
        boardRepositoryImpl.add(createSimpleBoardWith(boardId));

        DomainEventBus domainEventBus = new DomainEventBus();
        NotifyBoard notifyBoard = new NotifyBoard(boardRepositoryImpl, domainEventBus);
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
        Assert.assertEquals(1, boardRepositoryImpl.findById(boardId).get().getWidgetIds().size());
    }

    private Board createSimpleBoardWith(String boardId) {
        String teamId = "1";
        String boardName = "first";
        return new Board(boardId, teamId, boardName);
    }
}
