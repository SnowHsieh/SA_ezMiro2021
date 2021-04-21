package ntut.csie.selab.adapter.widget;

import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.usecase.widget.WidgetRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WidgetRepositoryImpl implements WidgetRepository {
    List<Widget> widgets;

    public WidgetRepositoryImpl() {
        this.widgets = new ArrayList<>();
    }

    @Override
    public void add(Widget widget) {
        this.widgets.add(widget);
    }

    @Override
    public Optional<Widget> findById(final String widgetId) {
        return widgets.stream().filter(e -> e.getId().equals(widgetId)).findFirst();
    }
}
