package ntut.csie.selab.usecase.widget;

import ntut.csie.selab.adapter.board.BoardRepositoryImpl;
import ntut.csie.selab.adapter.widget.WidgetRepositoryImpl;
import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.eventHandler.NotifyBoard;
import ntut.csie.selab.usecase.widget.delete.DeleteStickyNoteInput;
import ntut.csie.selab.usecase.widget.delete.DeleteStickyNoteOutput;
import ntut.csie.selab.usecase.widget.delete.DeleteStickyNoteUseCase;
import org.junit.Assert;
import org.junit.Test;

public class DeleteStickyNoteUseCaseTest {

    @Test
    public void delete_sticky_not_should_successd() {
        // Arrange
        WidgetRepository widgetRepository = new WidgetRepositoryImpl();
        String stickyNoteId = "1";
        Coordinate stickyNoteCoordinate = new Coordinate(1, 1, 2, 2);
        Widget stickyNote = new StickyNote(stickyNoteId, "0", stickyNoteCoordinate);
        widgetRepository.add(stickyNote);

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
        BoardRepository boardRepository = new BoardRepositoryImpl();
        String boardId = "boardId";
        String stickyNoteId = "stickyNoteId";

        boardRepository.add(createBoardHasStickNoteWith(boardId, stickyNoteId));

        WidgetRepository widgetRepository = new WidgetRepositoryImpl();
        Coordinate stickyNoteCoordinate = new Coordinate(1, 1, 2, 2);
        Widget stickyNote = new StickyNote(stickyNoteId, "boardId", stickyNoteCoordinate);
        widgetRepository.add(stickyNote);
        stickyNote.clearDomainEvents();

        DomainEventBus domainEventBus = new DomainEventBus();
        NotifyBoard notifyBoard = new NotifyBoard(boardRepository, domainEventBus);
        domainEventBus.register(notifyBoard);

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
        Assert.assertEquals(2, domainEventBus.getCount());
    }

    private Board createBoardHasStickNoteWith(String boardId, String stickyNoteId) {
        String teamId = "1";
        String boardName = "first";
        Board board = new Board(boardId, teamId, boardName);
        board.getWidgetIds().add(stickyNoteId);
        return board;
    }
}
