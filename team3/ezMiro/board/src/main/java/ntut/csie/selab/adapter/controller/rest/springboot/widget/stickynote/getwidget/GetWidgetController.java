package ntut.csie.selab.adapter.controller.rest.springboot.widget.stickynote.getwidget;

import ntut.csie.selab.adapter.presenter.widget.getwidget.StickyNoteViewModel;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.usecase.widget.StickyNoteDto;
import ntut.csie.selab.usecase.widget.StickyNoteDtoMapper;
import ntut.csie.selab.usecase.widget.query.getwidget.GetWidgetInput;
import ntut.csie.selab.usecase.widget.query.getwidget.GetWidgetOutput;
import ntut.csie.selab.usecase.widget.query.getwidget.GetWidgetUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin( origins = "${CORS_URLS}")
public class GetWidgetController {
    private GetWidgetUseCase getWidgetUseCase;

    @Autowired
    public GetWidgetController(GetWidgetUseCase getWidgetUseCase) {
        this.getWidgetUseCase = getWidgetUseCase;
    }

    @GetMapping(path = "/${EZ_MIRO_PREFIX}/boards/{boardId}/widgets/sticky-notes/{widgetId}", produces = "application/json")
    public StickyNoteViewModel getWidget(@PathVariable("widgetId") String widgetId) {
        GetWidgetInput input = new GetWidgetInput();
        GetWidgetOutput output = new GetWidgetOutput();
        input.setWidgetId(widgetId);

        StickyNoteDtoMapper stickyNoteDtoMapper = new StickyNoteDtoMapper();
        StickyNoteDto stickyNoteDto;

        getWidgetUseCase.execute(input, output);
        stickyNoteDto = stickyNoteDtoMapper.domainToDto((StickyNote) output.getWidget());

        return new StickyNoteViewModel(stickyNoteDto);
    }
}
