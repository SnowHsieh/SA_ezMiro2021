package ntut.csie.selab.adapter.controller.rest.springboot.board.getcontent;

import ntut.csie.selab.adapter.board.BoardRepositoryImpl;
import ntut.csie.selab.adapter.presenter.board.getcontent.BoardContentViewModel;
import ntut.csie.selab.adapter.widget.WidgetRepositoryImpl;
import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.board.query.getcontent.GetBoardContentInput;
import ntut.csie.selab.usecase.board.query.getcontent.GetBoardContentOutput;
import ntut.csie.selab.usecase.board.query.getcontent.GetBoardContentUseCase;
import ntut.csie.selab.usecase.widget.WidgetDto;
import ntut.csie.selab.usecase.widget.WidgetMapper;
import ntut.csie.selab.usecase.widget.WidgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class GetBoardContentController {
    private GetBoardContentUseCase getBoardContentUseCase;

    @GetMapping(path = "/ez-miro/boards/{boardId}/content", produces = "application/json")
    public BoardContentViewModel getBoardContent(@PathVariable("boardId") String boardId) {
        GetBoardContentInput input = new GetBoardContentInput();
        GetBoardContentOutput output = new GetBoardContentOutput();
        BoardRepository boardRepository = new BoardRepositoryImpl();
        WidgetRepository widgetRepository = new WidgetRepositoryImpl();
        getBoardContentUseCase = new GetBoardContentUseCase(boardRepository, widgetRepository);
        create_single_board_with_event_storming(boardRepository, widgetRepository);
        input.setBoardId(boardId);
        WidgetMapper widgetMapper = new WidgetMapper();
        List<WidgetDto> widgetDtos = new ArrayList<>();

        getBoardContentUseCase.execute(input, output);
        widgetDtos = widgetMapper.domainToDto(output.getWidgets());
        BoardContentViewModel boardContentViewModel = new BoardContentViewModel(output.getBoardId(), widgetDtos);
        return boardContentViewModel;
    }

    @GetMapping(path = "/")
    public String home(){
        return "Hi";
    }

    private void create_single_board_with_event_storming(BoardRepository boardRepository, WidgetRepository widgetRepository) {
        String boardId = "firstId";
        Board board = new Board(boardId,"firstTeam", "firstBoard");
        boardRepository.add(board);

        Widget readModel = new StickyNote("readModelId", boardId, new Coordinate(0, 200, 100, 300));
        widgetRepository.add(readModel);
        board.commitWidgetCreation(boardId, "readModelId");

        Widget command = new StickyNote("commandId", boardId, new Coordinate(150, 200, 250, 300));
        widgetRepository.add(command);
        board.commitWidgetCreation(boardId, "commandId");

        Widget aggregate = new StickyNote("aggregateId", boardId, new Coordinate(200, 0, 300, 100));
        widgetRepository.add(aggregate);
        board.commitWidgetCreation(boardId, "aggregateId");

        Widget domainEvent = new StickyNote("domainEventId", boardId, new Coordinate(300, 200, 400, 300));
        widgetRepository.add(domainEvent);
        board.commitWidgetCreation(boardId, "domainEventId");
    }
}
