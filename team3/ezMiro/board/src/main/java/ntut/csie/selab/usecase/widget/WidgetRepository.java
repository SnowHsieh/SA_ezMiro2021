package ntut.csie.selab.usecase.widget;

import ntut.csie.selab.entity.model.Widget;

import java.util.Optional;

public interface WidgetRepository {
    void add(Widget widget);

    Optional<Widget> findById(final String stickyNoteId);
}
