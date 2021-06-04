package ntut.csie.selab.usecase.widget.query.getwidget;

import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.usecase.widget.StickyNoteRepository;

import java.util.Optional;

public class GetWidgetUseCase {
    private StickyNoteRepository stickyNoteRepository;

    public GetWidgetUseCase(StickyNoteRepository stickyNoteRepository) {
        this.stickyNoteRepository = stickyNoteRepository;
    }

    public void execute(GetWidgetInput input, GetWidgetOutput output) {
        Optional<Widget> widget = stickyNoteRepository.findById(input.getWidgetId());

        if (widget.isPresent()) {
            output.setWidget(widget.get());
        } else {
            throw new RuntimeException("Widget not found, widget id = " + input.getWidgetId());
        }
    }
}
