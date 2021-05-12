package ntut.csie.selab.usecase.board.query.getcontent;

import ntut.csie.selab.adapter.board.BoardRepositoryImpl;
import ntut.csie.selab.adapter.presenter.board.getcontent.BoardContentViewModel;
import ntut.csie.selab.adapter.widget.WidgetRepositoryImpl;
import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.board.CommittedWidgetDto;
import ntut.csie.selab.usecase.board.CommittedWidgetMapper;
import ntut.csie.selab.usecase.widget.WidgetDto;
import ntut.csie.selab.usecase.widget.WidgetMapper;
import ntut.csie.selab.usecase.widget.WidgetRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class GetBoardContentUseCaseTest {

    @Test
    public void get_board_content_should_succeed() {
        // Arrange
        BoardRepository boardRepository = new BoardRepositoryImpl();
        WidgetRepository widgetRepository = new WidgetRepositoryImpl();
        GetBoardContentUseCase getBoardContentUseCase = new GetBoardContentUseCase(boardRepository, widgetRepository);
        GetBoardContentInput input = new GetBoardContentInput();
        GetBoardContentOutput output = new GetBoardContentOutput();
        create_single_board_with_event_storming(boardRepository, widgetRepository);
        input.setBoardId("firstId");
        WidgetMapper widgetMapper = new WidgetMapper();
        CommittedWidgetMapper committedWidgetMapper = new CommittedWidgetMapper();
        List<WidgetDto> widgetDtos;
        List<CommittedWidgetDto> committedWidgetDtos;

        // Act
        getBoardContentUseCase.execute(input, output);

        widgetDtos = widgetMapper.domainToDto(output.getWidgets());
        committedWidgetDtos = committedWidgetMapper.domainToDto(output.getCommittedWidgets());
        BoardContentViewModel boardContentViewModel = new BoardContentViewModel(output.getBoardId(), widgetDtos, committedWidgetDtos);

        // Assert
        Assert.assertEquals("firstId", boardContentViewModel.getBoardId());
        Assert.assertEquals(4, boardContentViewModel.getWidgetDtos().size());
        Assert.assertEquals(4, boardContentViewModel.getCommittedWidgetDtos().size());
    }

    private void create_single_board_with_event_storming(BoardRepository boardRepository, WidgetRepository widgetRepository) {
        String boardId = "firstId";
        Board board = new Board(boardId,"firstTeam", "firstBoard");
        boardRepository.add(board);

        Widget readModel = new StickyNote("readModelId", boardId, new Coordinate(0, 20, 10, 30));
        widgetRepository.add(readModel);
        board.commitWidgetCreation(boardId, "readModelId");

        Widget command = new StickyNote("commandId", boardId, new Coordinate(15, 20, 25, 30));
        widgetRepository.add(command);
        board.commitWidgetCreation(boardId, "commandId");

        Widget aggregate = new StickyNote("aggregateId", boardId, new Coordinate(20, 0, 30, 10));
        widgetRepository.add(aggregate);
        board.commitWidgetCreation(boardId, "aggregateId");

        Widget domainEvent = new StickyNote("domainEventId", boardId, new Coordinate(30, 20, 40, 30));
        widgetRepository.add(domainEvent);
        board.commitWidgetCreation(boardId, "domainEventId");
    }
}
