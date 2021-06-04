package ntut.csie.selab.adapter.controller.rest.springboot.board.getcontent;

import ntut.csie.selab.adapter.presenter.board.getcontent.BoardContentViewModel;
import ntut.csie.selab.entity.model.widget.WidgetType;
import ntut.csie.selab.usecase.board.CommittedWidgetDto;
import ntut.csie.selab.usecase.board.CommittedWidgetDtoMapper;
import ntut.csie.selab.usecase.board.query.getcontent.GetBoardContentInput;
import ntut.csie.selab.usecase.board.query.getcontent.GetBoardContentOutput;
import ntut.csie.selab.usecase.board.query.getcontent.GetBoardContentUseCase;
import ntut.csie.selab.usecase.widget.LineDto;
import ntut.csie.selab.usecase.widget.LineDtoMapper;
import ntut.csie.selab.usecase.widget.StickyNoteDto;
import ntut.csie.selab.usecase.widget.StickyNoteDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        List<StickyNoteDto> stickyNoteDtos;
        List<LineDto> lineDtos;
        List<CommittedWidgetDto> committedWidgetDtos;
        StickyNoteDtoMapper stickyNoteDtoMapper = new StickyNoteDtoMapper();
        LineDtoMapper lineDtoMapper = new LineDtoMapper();
        CommittedWidgetDtoMapper committedWidgetDtoMapper = new CommittedWidgetDtoMapper();
        input.setBoardId(boardId);

        getBoardContentUseCase.execute(input, output);
        stickyNoteDtos = stickyNoteDtoMapper.domainToDto(output.getWidgets().stream().filter(widget -> widget.getType().equals(WidgetType.STICKY_NOTE.getType())).collect(Collectors.toList()));
        lineDtos = lineDtoMapper.domainToDto(output.getWidgets().stream().filter(widget -> widget.getType().equals(WidgetType.LINE.getType())).collect(Collectors.toList()));
        committedWidgetDtos = committedWidgetDtoMapper.domainToDto(output.getCommittedWidgets());
        return new BoardContentViewModel(output.getBoardId(), stickyNoteDtos, lineDtos, committedWidgetDtos);
    }
}
