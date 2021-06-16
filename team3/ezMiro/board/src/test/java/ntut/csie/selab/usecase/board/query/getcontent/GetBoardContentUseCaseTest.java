package ntut.csie.selab.usecase.board.query.getcontent;

import ntut.csie.selab.adapter.board.BoardRepositoryInMemoryImpl;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.LineRepositoryPeer;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.StickyNoteRepositoryPeer;
import ntut.csie.selab.adapter.presenter.board.getcontent.BoardContentViewModel;
import ntut.csie.selab.adapter.widget.LineRepositoryImpl;
import ntut.csie.selab.adapter.widget.StickyNoteRepositoryImpl;
import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.widget.Position;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.entity.model.widget.WidgetType;
import ntut.csie.selab.usecase.JpaApplicationTest;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.board.CommittedWidgetDto;
import ntut.csie.selab.usecase.board.CommittedWidgetDtoMapper;
import ntut.csie.selab.usecase.widget.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes= JpaApplicationTest.class)
@Rollback(false)
public class GetBoardContentUseCaseTest {

    @Autowired
    private StickyNoteRepositoryPeer stickyNoteRepositoryPeer;

    @Autowired
    private LineRepositoryPeer lineRepositoryPeer;

    @Test
    public void get_board_content_should_succeed() {
        // Arrange
        BoardRepository boardRepository = new BoardRepositoryInMemoryImpl();
        StickyNoteRepository stickyNoteRepository = new StickyNoteRepositoryImpl(stickyNoteRepositoryPeer);
        LineRepository lineRepository = new LineRepositoryImpl(lineRepositoryPeer);
        GetBoardContentUseCase getBoardContentUseCase = new GetBoardContentUseCase(boardRepository, stickyNoteRepository, lineRepository);
        GetBoardContentInput input = new GetBoardContentInput();
        GetBoardContentOutput output = new GetBoardContentOutput();
        create_single_board_with_event_storming(boardRepository, stickyNoteRepository);
        input.setBoardId("firstId");
        StickyNoteDtoMapper stickyNoteDtoMapper = new StickyNoteDtoMapper();
        LineDtoMapper lineDtoMapper = new LineDtoMapper();
        CommittedWidgetDtoMapper committedWidgetDtoMapper = new CommittedWidgetDtoMapper();
        List<StickyNoteDto> stickyNoteDtos;
        List<LineDto> lineDtos;
        List<CommittedWidgetDto> committedWidgetDtos;

        // Act
        getBoardContentUseCase.execute(input, output);

        stickyNoteDtos = stickyNoteDtoMapper.domainToDto(output.getWidgets().stream().filter(widget -> widget.getType().equals(WidgetType.STICKY_NOTE.getType())).collect(Collectors.toList()));
        lineDtos = lineDtoMapper.domainToDto(output.getWidgets().stream().filter(widget -> widget.getType().equals(WidgetType.LINE.getType())).collect(Collectors.toList()));
        committedWidgetDtos = committedWidgetDtoMapper.domainToDto(output.getCommittedWidgets());
        BoardContentViewModel boardContentViewModel = new BoardContentViewModel(output.getBoardId(), stickyNoteDtos, lineDtos, committedWidgetDtos);

        // Assert
        Assert.assertEquals("firstId", boardContentViewModel.getBoardId());
        Assert.assertEquals(4, boardContentViewModel.getStickyNoteDtos().size());
        Assert.assertEquals(0, boardContentViewModel.getLineDtos().size());
        Assert.assertEquals(4, boardContentViewModel.getCommittedWidgetDtos().size());
    }

    private void create_single_board_with_event_storming(BoardRepository boardRepository, StickyNoteRepository stickyNoteRepository) {
        String boardId = "firstId";
        Board board = new Board(boardId,"firstTeam", "firstBoard");
        boardRepository.save(board);

        Widget readModel = new StickyNote("readModelId", boardId, new Position(0, 20, 10, 30));
        stickyNoteRepository.save(readModel);
        board.commitWidgetCreation("readModelId");

        Widget command = new StickyNote("commandId", boardId, new Position(15, 20, 25, 30));
        stickyNoteRepository.save(command);
        board.commitWidgetCreation("commandId");

        Widget aggregate = new StickyNote("aggregateId", boardId, new Position(20, 0, 30, 10));
        stickyNoteRepository.save(aggregate);
        board.commitWidgetCreation("aggregateId");

        Widget domainEvent = new StickyNote("domainEventId", boardId, new Position(30, 20, 40, 30));
        stickyNoteRepository.save(domainEvent);
        board.commitWidgetCreation("domainEventId");
    }
}
