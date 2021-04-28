package ntut.csie.selab.adapter.controller.rest.springboot.board.getcontent;

import ntut.csie.selab.adapter.board.BoardRepositoryImpl;
import ntut.csie.selab.adapter.presenter.board.getcontent.BoardContentViewModel;
import ntut.csie.selab.adapter.widget.WidgetRepositoryImpl;
import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.board.create.CreateBoardInput;
import ntut.csie.selab.usecase.board.create.CreateBoardOutput;
import ntut.csie.selab.usecase.board.create.CreateBoardUseCase;
import ntut.csie.selab.usecase.board.query.getcontent.GetBoardContentInput;
import ntut.csie.selab.usecase.board.query.getcontent.GetBoardContentOutput;
import ntut.csie.selab.usecase.board.query.getcontent.GetBoardContentUseCase;
import ntut.csie.selab.usecase.eventHandler.NotifyBoard;
import ntut.csie.selab.usecase.widget.WidgetDto;
import ntut.csie.selab.usecase.widget.WidgetMapper;
import ntut.csie.selab.usecase.widget.WidgetRepository;
import ntut.csie.selab.usecase.widget.create.CreateStickyNoteInput;
import ntut.csie.selab.usecase.widget.create.CreateStickyNoteOutput;
import ntut.csie.selab.usecase.widget.create.CreateStickyNoteUseCase;
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
        boardId = createSingleBoardWithEventStorming(boardRepository, widgetRepository);
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

    private String createSingleBoardWithEventStorming(BoardRepository boardRepository, WidgetRepository widgetRepository) {
        DomainEventBus domainEventBus = new DomainEventBus();
        domainEventBus.register(new NotifyBoard(boardRepository, domainEventBus));
        CreateBoardOutput createBoardOutput = createSingleBoard(boardRepository, domainEventBus);
        String boardId = createBoardOutput.getBoardId();

        String stickyNoteId = createStickyNoteIn(boardId, new Coordinate(0, 200, 100, 300), widgetRepository, domainEventBus);
        setColorOfWidgetIn(widgetRepository, stickyNoteId, "green");

        stickyNoteId = createStickyNoteIn(boardId, new Coordinate(150, 200, 250, 300), widgetRepository, domainEventBus);
        setColorOfWidgetIn(widgetRepository, stickyNoteId, "blue");

        stickyNoteId = createStickyNoteIn(boardId, new Coordinate(200, 0, 300, 100), widgetRepository, domainEventBus);
        setColorOfWidgetIn(widgetRepository, stickyNoteId, "yellow");

        stickyNoteId = createStickyNoteIn(boardId, new Coordinate(300, 200, 400, 300), widgetRepository, domainEventBus);
        setColorOfWidgetIn(widgetRepository, stickyNoteId, "orange");

        return boardId;
    }

    private void setColorOfWidgetIn (WidgetRepository widgetRepository, String stickyNoteId, String color) {
        Widget widget = widgetRepository.findById(stickyNoteId).get();
        widget.setColor(color);
    }

    private String createStickyNoteIn(String boardId, Coordinate coordinate, WidgetRepository widgetRepository, DomainEventBus domainEventBus) {
        CreateStickyNoteUseCase createStickyNoteUseCase = new CreateStickyNoteUseCase(widgetRepository, domainEventBus);
        CreateStickyNoteInput createStickyNoteInput = new CreateStickyNoteInput();
        CreateStickyNoteOutput createStickyNoteOutput = new CreateStickyNoteOutput();

        createStickyNoteInput.setBoardId(boardId);
        createStickyNoteInput.setCoordinate(coordinate);

        createStickyNoteUseCase.execute(createStickyNoteInput, createStickyNoteOutput);

        return createStickyNoteOutput.getStickyNoteId();
    }

    private CreateBoardOutput createSingleBoard(BoardRepository boardRepository, DomainEventBus domainEventBus) {

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, domainEventBus);
        CreateBoardInput createBoardInput = new CreateBoardInput();
        CreateBoardOutput createBoardOutput = new CreateBoardOutput();
        createBoardInput.setTeamId("1");
        createBoardInput.setBoardName("firstBoard");
        createBoardUseCase.execute(createBoardInput, createBoardOutput);
        return createBoardOutput;
    }
}
