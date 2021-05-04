package ntut.csie.selab.adapter.controller.rest.springboot.widget.getwidget;

import ntut.csie.selab.adapter.presenter.board.getcontent.BoardContentViewModel;
import ntut.csie.selab.adapter.presenter.widget.getwidget.WidgetViewModel;
import ntut.csie.selab.usecase.board.query.getcontent.GetBoardContentInput;
import ntut.csie.selab.usecase.board.query.getcontent.GetBoardContentOutput;
import ntut.csie.selab.usecase.widget.WidgetDto;
import ntut.csie.selab.usecase.widget.WidgetMapper;
import ntut.csie.selab.usecase.widget.query.getwidget.GetWidgetInput;
import ntut.csie.selab.usecase.widget.query.getwidget.GetWidgetOutput;
import ntut.csie.selab.usecase.widget.query.getwidget.GetWidgetUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin( origins = "${CORS_URLS}")
public class GetWidgetController {
    private GetWidgetUseCase getWidgetUseCase;

    @Autowired
    public GetWidgetController(GetWidgetUseCase getWidgetUseCase) {
        this.getWidgetUseCase = getWidgetUseCase;
    }

    @GetMapping(path = "/${EZ_MIRO_PREFIX}/boards/{boardId}/widgets/{widgetId}", produces = "application/json")
    public WidgetViewModel getWidget(@PathVariable("widgetId") String widgetId) {
        GetWidgetInput input = new GetWidgetInput();
        GetWidgetOutput output = new GetWidgetOutput();
        input.setWidgetId(widgetId);

        WidgetMapper widgetMapper = new WidgetMapper();
        WidgetDto widgetDto;

        getWidgetUseCase.execute(input, output);
        widgetDto = widgetMapper.domainToDto(output.getWidget());

        return new WidgetViewModel(widgetDto);
    }
}
