package ntut.csie.selab.usecase.widget.query.getwidget;

import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.usecase.widget.WidgetRepository;

import java.util.Optional;

public class GetWidgetUseCase {
    private WidgetRepository widgetRepository;

    public GetWidgetUseCase(WidgetRepository widgetRepository) {
        this.widgetRepository = widgetRepository;
    }

    public void execute(GetWidgetInput input, GetWidgetOutput output) {
        Optional<Widget> widget = widgetRepository.findById(input.getWidgetId());

        if (widget.isPresent()) {
            output.setWidget(widget.get());
        } else {
            throw new RuntimeException("Widget not found, widget id = " + input.getWidgetId());
        }
    }
}
