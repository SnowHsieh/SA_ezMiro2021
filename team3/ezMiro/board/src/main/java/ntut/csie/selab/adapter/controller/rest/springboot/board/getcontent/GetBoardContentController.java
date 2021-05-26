package ntut.csie.selab.adapter.controller.rest.springboot.board.getcontent;

import ntut.csie.selab.adapter.presenter.board.getcontent.BoardContentViewModel;
import ntut.csie.selab.usecase.board.CommittedWidgetDto;
import ntut.csie.selab.usecase.board.CommittedWidgetDtoMapper;
import ntut.csie.selab.usecase.board.query.getcontent.GetBoardContentInput;
import ntut.csie.selab.usecase.board.query.getcontent.GetBoardContentOutput;
import ntut.csie.selab.usecase.board.query.getcontent.GetBoardContentUseCase;
import ntut.csie.selab.usecase.widget.WidgetDto;
import ntut.csie.selab.usecase.widget.WidgetDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        WidgetDtoMapper widgetDtoMapper = new WidgetDtoMapper();
        CommittedWidgetDtoMapper committedWidgetDtoMapper = new CommittedWidgetDtoMapper();
        input.setBoardId(boardId);

        getBoardContentUseCase.execute(input, output);
        widgetDtos = widgetDtoMapper.domainToDto(output.getWidgets());
        committedWidgetDtos = committedWidgetDtoMapper.domainToDto(output.getCommittedWidgets());
        return new BoardContentViewModel(output.getBoardId(), widgetDtos, committedWidgetDtos);
    }
}
