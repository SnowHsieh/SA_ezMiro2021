package ntut.csie.selab.usecase.widget;

import ntut.csie.selab.entity.model.widget.Widget;

import java.util.List;
import java.util.Optional;

public interface LineRepository {
    void save(Widget line);

    void delete(Widget line);

    Optional<Widget> findById(final String lineId);

    List<Widget> findAllByHeadWidgetOrTailWidget(final String widgetId);

    void saveAll(List<Widget> lines);
}
