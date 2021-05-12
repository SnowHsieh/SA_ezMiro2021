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
import ntut.csie.selab.usecase.board.CommittedWidgetDto;
import ntut.csie.selab.usecase.board.CommittedWidgetMapper;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "${CORS_URLS}")
public class GetBoardContentController {
    private GetBoardContentUseCase getBoardContentUseCase;

    @Autowired
    public GetBoardContentController(GetBoardContentUseCase getBoardContentUseCase) {
        this.getBoardContentUseCase = getBoardContentUseCase;
    }

    @GetMapping(path = "/${EZ_MIRO_PREFIX}/boards/{boardId}/content", produces = "application/json")
    public BoardContentViewModel getBoardContent(@PathVariable("boardId") String boardId) {
        GetBoardContentInput input = new GetBoardContentInput();
        GetBoardContentOutput output = new GetBoardContentOutput();
        List<WidgetDto> widgetDtos;
        List<CommittedWidgetDto> committedWidgetDtos;
        WidgetMapper widgetMapper = new WidgetMapper();
        CommittedWidgetMapper committedWidgetMapper = new CommittedWidgetMapper();
        input.setBoardId(boardId);

        getBoardContentUseCase.execute(input, output);
        widgetDtos = widgetMapper.domainToDto(output.getWidgets());
        committedWidgetDtos = committedWidgetMapper.domainToDto(output.getCommittedWidgets());
        return new BoardContentViewModel(output.getBoardId(), widgetDtos, committedWidgetDtos);
    }
}
