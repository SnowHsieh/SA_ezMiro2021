package ntut.csie.selab.adapter.presenter.widget.getwidget;

import ntut.csie.selab.usecase.widget.WidgetDto;

public class WidgetViewModel {
    WidgetDto widgetDto;

    public WidgetViewModel(WidgetDto widgetDto) {
        this.widgetDto = widgetDto;
    }

    public WidgetDto getWidgetDto() { return widgetDto; }
}
