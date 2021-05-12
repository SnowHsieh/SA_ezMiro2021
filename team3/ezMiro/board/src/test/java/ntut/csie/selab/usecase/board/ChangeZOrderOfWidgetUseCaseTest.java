package ntut.csie.selab.usecase.board;

import ntut.csie.selab.adapter.board.BoardRepositoryImpl;
import ntut.csie.selab.adapter.widget.WidgetRepositoryImpl;
import ntut.csie.selab.domain.MockFactory;
import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.edit.zorder.ChangeZOrderOfWidgetInput;
import ntut.csie.selab.usecase.board.edit.zorder.ChangeZOrderOfWidgetOutput;
import ntut.csie.selab.usecase.board.edit.zorder.ChangeZOrderOfWidgetUseCase;
import ntut.csie.selab.usecase.widget.WidgetRepository;
import org.junit.Assert;
import org.junit.Test;

public class ChangeZOrderOfWidgetUseCaseTest {

    @Test
    public void change_z_order_of_sticky_note_should_succeed() {
        // Arrange
        BoardRepository boardRepository = new BoardRepositoryImpl();
        String boardId = "boardId";
        Board board = MockFactory.createBoard(boardId);
        boardRepository.add(board);

        WidgetRepository widgetRepository = new WidgetRepositoryImpl();
        String widgetId1 = "widgetId1";
        String widgetId2 = "widgetId2";
        String widgetId3 = "widgetId3";
        widgetRepository.add(MockFactory.createStickyNoteIn(board, widgetId1));
        widgetRepository.add(MockFactory.createStickyNoteIn(board, widgetId2));
        widgetRepository.add(MockFactory.createStickyNoteIn(board, widgetId3));

        DomainEventBus domainEventBus = new DomainEventBus();
        ChangeZOrderOfWidgetInput input = new ChangeZOrderOfWidgetInput();
        input.setBoardId(boardId);
        input.setWidgetId(widgetId1);
        input.setZOrder(2);
        ChangeZOrderOfWidgetOutput output = new ChangeZOrderOfWidgetOutput();

        ChangeZOrderOfWidgetUseCase changeZOrderOfWidgetUseCase = new ChangeZOrderOfWidgetUseCase(boardRepository, domainEventBus);

        // Act
        changeZOrderOfWidgetUseCase.execute(input, output);

        // Assert
        Assert.assertEquals(widgetId1, output.getWidgetId());
        Assert.assertEquals(2, output.getZOrder());

        // round 2
        input.setBoardId(boardId);
        input.setWidgetId(widgetId2);
        input.setZOrder(2);

        changeZOrderOfWidgetUseCase.execute(input, output);

        Assert.assertEquals(widgetId2, output.getWidgetId());
        Assert.assertEquals(2, output.getZOrder());

        Assert.assertEquals(1, board.getCommittedWidgetBy(widgetId1).get().getZOrder());
        Assert.assertEquals(2, board.getCommittedWidgetBy(widgetId2).get().getZOrder());
        Assert.assertEquals(0, board.getCommittedWidgetBy(widgetId3).get().getZOrder());
    }

    @Test
    public void change_z_order_of_sticky_note_to_neighbor_should_succeed() {
        // Arrange
        BoardRepository boardRepository = new BoardRepositoryImpl();
        String boardId = "boardId";
        Board board = MockFactory.createBoard(boardId);
        boardRepository.add(board);

        WidgetRepository widgetRepository = new WidgetRepositoryImpl();
        String widgetId1 = "widgetId1";
        String widgetId2 = "widgetId2";
        String widgetId3 = "widgetId3";
        String widgetId4 = "widgetId4";
        widgetRepository.add(MockFactory.createStickyNoteIn(board, widgetId1));
        widgetRepository.add(MockFactory.createStickyNoteIn(board, widgetId2));
        widgetRepository.add(MockFactory.createStickyNoteIn(board, widgetId3));
        widgetRepository.add(MockFactory.createStickyNoteIn(board, widgetId4));

        DomainEventBus domainEventBus = new DomainEventBus();
        ChangeZOrderOfWidgetInput input = new ChangeZOrderOfWidgetInput();
        input.setBoardId(boardId);
        input.setWidgetId(widgetId2);
        input.setZOrder(2);
        ChangeZOrderOfWidgetOutput output = new ChangeZOrderOfWidgetOutput();

        ChangeZOrderOfWidgetUseCase changeZOrderOfWidgetUseCase = new ChangeZOrderOfWidgetUseCase(boardRepository, domainEventBus);

        // Act
        changeZOrderOfWidgetUseCase.execute(input, output);

        // Assert
        Assert.assertEquals(widgetId2, output.getWidgetId());
        Assert.assertEquals(2, output.getZOrder());
    }

    @Test
    public void change_z_order_of_sticky_note_revert_should_succeed() {
        // Arrange
        BoardRepository boardRepository = new BoardRepositoryImpl();
        String boardId = "boardId";
        Board board = MockFactory.createBoard(boardId);
        boardRepository.add(board);

        WidgetRepository widgetRepository = new WidgetRepositoryImpl();
        String widgetId1 = "widgetId1";
        String widgetId2 = "widgetId2";
        String widgetId3 = "widgetId3";
        widgetRepository.add(MockFactory.createStickyNoteIn(board, widgetId1));
        widgetRepository.add(MockFactory.createStickyNoteIn(board, widgetId2));
        widgetRepository.add(MockFactory.createStickyNoteIn(board, widgetId3));

        DomainEventBus domainEventBus = new DomainEventBus();
        ChangeZOrderOfWidgetInput input = new ChangeZOrderOfWidgetInput();
        input.setBoardId(boardId);
        input.setWidgetId(widgetId3);
        input.setZOrder(0);
        ChangeZOrderOfWidgetOutput output = new ChangeZOrderOfWidgetOutput();

        ChangeZOrderOfWidgetUseCase changeZOrderOfWidgetUseCase = new ChangeZOrderOfWidgetUseCase(boardRepository, domainEventBus);

        // Act
        changeZOrderOfWidgetUseCase.execute(input, output);

        // Assert
        Assert.assertEquals(widgetId3, output.getWidgetId());
        Assert.assertEquals(0, output.getZOrder());
    }

    @Test
    public void change_z_order_of_sticky_note_to_neighbor_revert_should_succeed() {
        // Arrange
        BoardRepository boardRepository = new BoardRepositoryImpl();
        String boardId = "boardId";
        Board board = MockFactory.createBoard(boardId);
        boardRepository.add(board);

        WidgetRepository widgetRepository = new WidgetRepositoryImpl();
        String widgetId1 = "widgetId1";
        String widgetId2 = "widgetId2";
        String widgetId3 = "widgetId3";
        String widgetId4 = "widgetId4";
        widgetRepository.add(MockFactory.createStickyNoteIn(board, widgetId1));
        widgetRepository.add(MockFactory.createStickyNoteIn(board, widgetId2));
        widgetRepository.add(MockFactory.createStickyNoteIn(board, widgetId3));
        widgetRepository.add(MockFactory.createStickyNoteIn(board, widgetId4));

        DomainEventBus domainEventBus = new DomainEventBus();
        ChangeZOrderOfWidgetInput input = new ChangeZOrderOfWidgetInput();
        input.setBoardId(boardId);
        input.setWidgetId(widgetId3);
        input.setZOrder(1);
        ChangeZOrderOfWidgetOutput output = new ChangeZOrderOfWidgetOutput();

        ChangeZOrderOfWidgetUseCase changeZOrderOfWidgetUseCase = new ChangeZOrderOfWidgetUseCase(boardRepository, domainEventBus);

        // Act
        changeZOrderOfWidgetUseCase.execute(input, output);

        // Assert
        Assert.assertEquals(widgetId3, output.getWidgetId());
        Assert.assertEquals(1, output.getZOrder());
    }
}
