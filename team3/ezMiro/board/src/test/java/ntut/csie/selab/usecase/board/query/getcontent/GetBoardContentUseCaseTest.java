package ntut.csie.selab.usecase.board.query.getcontent;

import ntut.csie.selab.adapter.board.BoardRepositoryInMemoryImpl;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.StickyNoteRepositoryPeer;
import ntut.csie.selab.adapter.presenter.board.getcontent.BoardContentViewModel;
import ntut.csie.selab.adapter.widget.StickyNoteRepositoryImpl;
import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.usecase.JpaApplicationTest;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.board.CommittedWidgetDto;
import ntut.csie.selab.usecase.board.CommittedWidgetDtoMapper;
import ntut.csie.selab.usecase.widget.StickyNoteDto;
import ntut.csie.selab.usecase.widget.StickyNoteDtoMapper;
import ntut.csie.selab.usecase.widget.WidgetRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes= JpaApplicationTest.class)
@Rollback(false)
public class GetBoardContentUseCaseTest {

    @Autowired
    private StickyNoteRepositoryPeer stickyNoteRepositoryPeer;

    @Test
    public void get_board_content_should_succeed() {
        // Arrange
        BoardRepository boardRepository = new BoardRepositoryInMemoryImpl();
        WidgetRepository widgetRepository = new StickyNoteRepositoryImpl(stickyNoteRepositoryPeer);
        GetBoardContentUseCase getBoardContentUseCase = new GetBoardContentUseCase(boardRepository, widgetRepository);
        GetBoardContentInput input = new GetBoardContentInput();
        GetBoardContentOutput output = new GetBoardContentOutput();
        create_single_board_with_event_storming(boardRepository, widgetRepository);
        input.setBoardId("firstId");
        StickyNoteDtoMapper stickyNoteDtoMapper = new StickyNoteDtoMapper();
        CommittedWidgetDtoMapper committedWidgetDtoMapper = new CommittedWidgetDtoMapper();
        List<StickyNoteDto> stickyNoteDtos;
        List<CommittedWidgetDto> committedWidgetDtos;

        // Act
        getBoardContentUseCase.execute(input, output);

        stickyNoteDtos = stickyNoteDtoMapper.domainToDto(output.getWidgets());
        committedWidgetDtos = committedWidgetDtoMapper.domainToDto(output.getCommittedWidgets());
        BoardContentViewModel boardContentViewModel = new BoardContentViewModel(output.getBoardId(), stickyNoteDtos, committedWidgetDtos);

        // Assert
        Assert.assertEquals("firstId", boardContentViewModel.getBoardId());
        Assert.assertEquals(4, boardContentViewModel.getWidgetDtos().size());
        Assert.assertEquals(4, boardContentViewModel.getCommittedWidgetDtos().size());
    }

    private void create_single_board_with_event_storming(BoardRepository boardRepository, WidgetRepository widgetRepository) {
        String boardId = "firstId";
        Board board = new Board(boardId,"firstTeam", "firstBoard");
        boardRepository.save(board);

        Widget readModel = new StickyNote("readModelId", boardId, new Coordinate(0, 20, 10, 30));
        widgetRepository.save(readModel);
        board.commitWidgetCreation("readModelId");

        Widget command = new StickyNote("commandId", boardId, new Coordinate(15, 20, 25, 30));
        widgetRepository.save(command);
        board.commitWidgetCreation("commandId");

        Widget aggregate = new StickyNote("aggregateId", boardId, new Coordinate(20, 0, 30, 10));
        widgetRepository.save(aggregate);
        board.commitWidgetCreation("aggregateId");

        Widget domainEvent = new StickyNote("domainEventId", boardId, new Coordinate(30, 20, 40, 30));
        widgetRepository.save(domainEvent);
        board.commitWidgetCreation("domainEventId");
    }
}
